package com.example.proyectofinal;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up extends AppCompatActivity {

    //Registro con Realtime Database
    public EditText signupNameDB;
    public EditText signupUsernameDB;

    private FirebaseAuth auth;
    private EditText  signupEmail,  signupPassword;
    public Button signupButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        ImageView imageView = findViewById(R.id.vectorAtras);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Registro con Realtime Database
        signupNameDB  = findViewById(R.id.inputName);
        signupUsernameDB  = findViewById(R.id.inputUsuario);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.inputCorreo);
        signupPassword = findViewById(R.id.inputPassword);
        signupButton = findViewById(R.id.btnRegistrar);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                if (!validateName()|!validateEmail()|!validateUsername()|!validatePassword()){
                    return;
                }/*else{
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    String name = signupNameDB.getText().toString();
                    String email = signupEmailDB.getText().toString();
                    String username = signupUsernameDB.getText().toString();
                    String password = signupPasswordDB.getText().toString();

                    HelperClass helperClass = new HelperClass(name,email,username,password);
                    reference.child(username).setValue(helperClass);

                    Toast.makeText(Sign_up.this, "Te has registrado",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Sign_up.this, Sign_in.class);
                    startActivity(intent);
                }*/

                /*

                if (user.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if(pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }*/else{
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // Registro exitoso, obtén el UID del usuario
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userId = firebaseUser.getUid();

                                // Guarda los datos adicionales del usuario en Firebase Realtime Database
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                                userRef.child("name").setValue(signupNameDB.getText().toString());
                                userRef.child("email").setValue(signupEmail.getText().toString());
                                userRef.child("username").setValue(signupUsernameDB.getText().toString());
                                userRef.child("password").setValue(signupPassword.getText().toString());

                                // Notifica al usuario sobre el registro exitoso
                                Toast.makeText(Sign_up.this, "Registro Completo", Toast.LENGTH_SHORT).show();
                                // Inicia la actividad de inicio de sesión
                                startActivity(new Intent(Sign_up.this, Sign_in.class));
                                finish();
                            }else {
                                Toast.makeText(Sign_up.this, "Registro Fallido"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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

    //Registro con Realtime Database
    public Boolean validateName(){
        String val = signupNameDB.getText().toString();
        if (val.isEmpty()){
            signupNameDB.setError("No puede estar vacio");
            return false;
        }else {
            signupNameDB.setError(null);
            return true;
        }
    }

    public Boolean validateEmail(){
        String val = signupEmail.getText().toString();

        if (val.isEmpty()){
            signupEmail.setError("No puede estar vacio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            signupEmail.setError("Email inválido");
            return false;
        }else {
            signupEmail.setError(null);
            return true;
        }
    }

    public Boolean validateUsername(){
        String val = signupUsernameDB.getText().toString();
        if (val.isEmpty()){
            signupUsernameDB.setError("No puede estar vacio");
            return false;
        }else {
            signupUsernameDB.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = signupPassword.getText().toString();
        if (val.isEmpty()){
            signupPassword.setError("No puede estar vacio");
            return false;
        }else {
            signupPassword.setError(null);
            return true;
        }
    }
     //

}