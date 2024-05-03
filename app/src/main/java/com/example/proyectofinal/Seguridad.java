package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Seguridad extends AppCompatActivity {

    private EditText mContraseñaActualEditText;
    private EditText mNuevaContraseñaEditText;
    private EditText mConfirmarContraseñaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seguridad);

        mContraseñaActualEditText = findViewById(R.id.inputPassword);
        mNuevaContraseñaEditText = findViewById(R.id.inputnewPassword);
        mConfirmarContraseñaEditText = findViewById(R.id.inputcnPassword);

        Button actualizarContraseñaBtn = findViewById(R.id.btnactualizar);
        actualizarContraseñaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarContraseña();
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
    private void actualizarContraseña() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String contraseñaActual = mContraseñaActualEditText.getText().toString();
            String nuevaContraseña = mNuevaContraseñaEditText.getText().toString();
            String confirmarContraseña = mConfirmarContraseñaEditText.getText().toString();

            // Verificar si las contraseñas nuevas coinciden
            if (!nuevaContraseña.equals(confirmarContraseña)) {
                Toast.makeText(this, "Las nuevas contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar si la contraseña actual es correcta
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraseñaActual);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Contraseña actualizada correctamente
                                user.updatePassword(nuevaContraseña)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Seguridad.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(Seguridad.this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Contraseña actual incorrecta
                                Toast.makeText(Seguridad.this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void eliminarCuenta() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String contraseñaActual = mContraseñaActualEditText.getText().toString();

            // Verificar si la contraseña actual es vacía
            if (contraseñaActual.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu contraseña actual", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verificar si la contraseña actual es correcta
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraseñaActual);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Contraseña actual correcta, proceder con la eliminación de la cuenta
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Seguridad.this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();
                                                    // Iniciar la actividad de inicio de sesión
                                                    Intent intent = new Intent(Seguridad.this, Sign_in.class);
                                                    startActivity(intent);
                                                    // Cerrar la actividad actual
                                                    finish();
                                                } else {
                                                    Toast.makeText(Seguridad.this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Contraseña actual incorrecta
                                Toast.makeText(Seguridad.this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}