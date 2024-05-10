package com.example.proyectofinal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    // Registro con Realtime Database
    public EditText signupNameDB;
    public EditText signupUsernameDB;
    public EditText signupEmail;
    public EditText signupPassword;
    public Button signupButton;

    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        initializeViews();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void initializeViews() {
        signupNameDB = findViewById(R.id.inputName);
        signupUsernameDB = findViewById(R.id.inputUsuario);
        signupEmail = findViewById(R.id.inputCorreo);
        signupPassword = findViewById(R.id.inputPassword);
        signupButton = findViewById(R.id.btnRegistrar);

        auth = FirebaseAuth.getInstance();

        ImageView imageView = findViewById(R.id.vectorAtras);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textView = findViewById(R.id.registrese);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        String name = signupNameDB.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String username = signupUsernameDB.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        if (validateInput(name, email, username, password)) {
            createUser(email, password, name, username);
        }
    }

    public boolean validateInput(String name, String email, String username, String password) {
        boolean isValid = true;
        if (!validateName(name)) {
            isValid = false;
            signupNameDB.setError("No puede estar vacío");
        }
        if (!validateEmail(email)) {
            isValid = false;
            signupEmail.setError("No puede estar vacío o inválido");
        }
        if (!validateUsername(username)) {
            isValid = false;
            signupUsernameDB.setError("No puede estar vacío");
        }
        if (!validatePassword(password)) {
            isValid = false;
            signupPassword.setError("No puede estar vacío");
        }
        return isValid;
    }

    public boolean validateName(String name) {
        return !name.isEmpty();
    }

    public boolean validateEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validateUsername(String username) {
        return !username.isEmpty();
    }

    public boolean validatePassword(String password) {
        return !password.isEmpty();
    }

    private void createUser(String email, String password, String name, String username) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Registro exitoso, obtén el UID del usuario
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = firebaseUser.getUid();

                    // Guarda los datos adicionales del usuario en Firebase Realtime Database
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                    userRef.child("name").setValue(name);
                    userRef.child("email").setValue(email);
                    userRef.child("username").setValue(username);

                    // Notifica al usuario sobre el registro exitoso
                    Toast.makeText(SignUp.this, "Registro Completo", Toast.LENGTH_SHORT).show();
                    // Inicia la actividad de inicio de sesión
                    startActivity(new Intent(SignUp.this, SignIn.class));
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "Registro Fallido" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public int add(int a, int b) {
        return a + b;
    }
}
