package com.example.proyectofinal;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profile extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth =FirebaseAuth.getInstance();

        String email = "";
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textView = rootView.findViewById(R.id.occupation_textview);
        textView.setText(""+email);


        // Obtén una referencia al LinearLayout
        LinearLayout linearLayout = rootView.findViewById(R.id.linearLayout);
        LinearLayout linearLayout2 = rootView.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = rootView.findViewById(R.id.linearLayout3);
        LinearLayout linearLayout4 = rootView.findViewById(R.id.linearLayout4);
        LinearLayout linearLayout5 = rootView.findViewById(R.id.linearLayout5);

        // Agrega un OnClickListener al LinearLayout
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para abrir la actividad UpdateInfoActivity
                Intent intent = new Intent(getActivity(), updateInfo.class);

                // Inicia la actividad
                startActivity(intent);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén una referencia al BottomNavigationView que contiene tu menú
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

                // Simula un clic en el elemento del menú con el ID "carrito"
                bottomNavigationView.setSelectedItemId(R.id.carrito);

                // Configura el tinte del icono del elemento del menú "Carrito"
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.carrito);
                if (menuItem != null) {
                    Drawable icon = menuItem.getIcon();
                    if (icon != null) {
                        icon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.registro_color), PorterDuff.Mode.SRC_IN);
                        menuItem.setIcon(icon);
                    }
                }
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén una referencia al BottomNavigationView que contiene tu menú
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

                // Simula un clic en el elemento del menú con el ID "carrito"
                bottomNavigationView.setSelectedItemId(R.id.pedidos);

                // Configura el tinte del icono del elemento del menú "Carrito"
                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.pedidos);
                if (menuItem != null) {
                    Drawable icon = menuItem.getIcon();
                    if (icon != null) {
                        icon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.registro_color), PorterDuff.Mode.SRC_IN);
                        menuItem.setIcon(icon);
                    }
                }
            }
        });


        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), Sign_in.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para abrir la actividad UpdateInfoActivity
                Intent intent = new Intent(getActivity(), Seguridad.class);

                // Inicia la actividad
                startActivity(intent);
            }
        });


        return rootView;
    }
}