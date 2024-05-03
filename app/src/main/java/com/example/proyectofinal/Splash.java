package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash);

        // Obtén el TextView donde está tu texto
        TextView textView = findViewById(R.id.cybertech);

        // Obtén el texto completo
        String textoCompleto = textView.getText().toString();

        // Encuentra la posición de la parte del texto que quieres cambiar de color
        int inicio = textoCompleto.indexOf("TECH");
        int fin = inicio + "TECH".length();

        // Crea un SpannableString con el texto completo
        SpannableString spannableString = new SpannableString(textoCompleto);

        // Obtén el color azul_tech de colors.xml
        int colorAzulTech = ContextCompat.getColor(this, R.color.ingreso_color);

        // Aplica el color azul solo a la parte del texto que deseas
        ForegroundColorSpan colorAzul = new ForegroundColorSpan(colorAzulTech);
        spannableString.setSpan(colorAzul, inicio, fin, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Establece el SpannableString modificado en el TextView
        textView.setText(spannableString);


        Button boton = findViewById(R.id.btnIngresar);
        boton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // Crear un Intent para iniciar SignInActivity
                 Intent intent = new Intent(Splash.this, Sign_in.class);
                 // Iniciar SignInActivity
                 startActivity(intent);
             }
         });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}