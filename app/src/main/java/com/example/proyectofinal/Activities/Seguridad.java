package com.example.proyectofinal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofinal.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Seguridad extends AppCompatActivity {

    public EditText mContraseñaActualEditText;
    public EditText mNuevaContraseñaEditText;
    public EditText mConfirmarContraseñaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seguridad);

        ImageView imageView = findViewById(R.id.vectorAtras);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContraseñaActualEditText = findViewById(R.id.inputPassword);
        mNuevaContraseñaEditText = findViewById(R.id.inputnewPassword);
        mConfirmarContraseñaEditText = findViewById(R.id.inputcnPassword);

        Button actualizarContraseñaBtn = findViewById(R.id.btnactualizar);
        actualizarContraseñaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarEspaciosRequeridosLlenos()) {
                    actualizarContraseña();
                } else {
                    mostrarMensajeYError("Por favor completa todos los campos", mContraseñaActualEditText, mNuevaContraseñaEditText, mConfirmarContraseñaEditText);
                }
            }
        });

        Button eliminarCuentaBtn = findViewById(R.id.btneliminar);
        eliminarCuentaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCuenta();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean verificarEspaciosRequeridosLlenos() {
        return !TextUtils.isEmpty(mContraseñaActualEditText.getText().toString())
                && !TextUtils.isEmpty(mNuevaContraseñaEditText.getText().toString())
                && !TextUtils.isEmpty(mConfirmarContraseñaEditText.getText().toString());
    }

    private void actualizarContraseña() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String contraseñaActual = mContraseñaActualEditText.getText().toString();
            String nuevaContraseña = mNuevaContraseñaEditText.getText().toString();
            String confirmarContraseña = mConfirmarContraseñaEditText.getText().toString();

            if (!verificarCoincidenciaNuevasContraseñas(nuevaContraseña, confirmarContraseña)) {
                mostrarMensaje("Las nuevas contraseñas no coinciden");
                return;
            }

            reautenticarUsuario(user, contraseñaActual, nuevaContraseña);
        }
    }

    private boolean verificarCoincidenciaNuevasContraseñas(String nuevaContraseña, String confirmarContraseña) {
        return nuevaContraseña.equals(confirmarContraseña);
    }

    private void reautenticarUsuario(FirebaseUser user, String contraseñaActual, String nuevaContraseña) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraseñaActual);
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        actualizarContraseñaUsuario(user, nuevaContraseña);
                    } else {
                        mostrarMensaje("Contraseña actual incorrecta");
                    }
                });
    }

    private void actualizarContraseñaUsuario(FirebaseUser user, String nuevaContraseña) {
        user.updatePassword(nuevaContraseña)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mostrarMensaje("Contraseña actualizada correctamente");
                        finish();
                    } else {
                        mostrarMensaje("Error al actualizar la contraseña");
                    }
                });
    }



    private void eliminarCuenta() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String contraseñaActual = mContraseñaActualEditText.getText().toString();

            if (verificarContraseñaNoVacia(contraseñaActual)) {
                reautenticarYEliminarCuenta(user, contraseñaActual);
            } else {
                mostrarMensaje("Por favor, ingresa tu contraseña actual");
            }
        }
    }

    public boolean verificarContraseñaNoVacia(String contraseña) {
        return !contraseña.isEmpty();
    }

    private void reautenticarYEliminarCuenta(FirebaseUser user, String contraseñaActual) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraseñaActual);
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eliminarDatosUsuario(user);
                    } else {
                        mostrarMensaje("Contraseña actual incorrecta");
                    }
                });
    }

    private void eliminarDatosUsuario(FirebaseUser user) {
        String userId = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eliminarCuentaUsuario(user);
                    } else {
                        mostrarMensaje("Error al eliminar los datos del usuario");
                    }
                });
    }

    private void eliminarCuentaUsuario(FirebaseUser user) {
        user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mostrarMensaje("Cuenta eliminada correctamente");
                        iniciarActividadInicioSesion();
                        finish();
                    } else {
                        mostrarMensaje("Error al eliminar la cuenta");
                    }
                });
    }

    private void iniciarActividadInicioSesion() {
        Intent intent = new Intent(Seguridad.this, SignIn.class);
        startActivity(intent);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void mostrarMensajeYError(String mensaje, EditText... editTexts) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError("Campo requerido");
            } else {
                editText.setError(null); // Limpiar el error si el campo está lleno
            }
        }
    }
}