package com.example.proyectofinal.Activities;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectofinal.Fragments.AlertFragment;
import com.example.proyectofinal.Fragments.CartFragment;
import com.example.proyectofinal.Fragments.HomeFragment;
import com.example.proyectofinal.Fragments.OrdersFragment;
import com.example.proyectofinal.Fragments.ProfileFragment;
import com.example.proyectofinal.R;
import com.example.proyectofinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentFragment = new HomeFragment();
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.inicio) {
                currentFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.pedidos) {
                currentFragment = new OrdersFragment();
            } else if (item.getItemId() == R.id.carrito) {
                currentFragment = new CartFragment();
            } else if (item.getItemId() == R.id.notificacion) {
                currentFragment = new AlertFragment();
            } else if (item.getItemId() == R.id.perfil) {
                currentFragment = new ProfileFragment();
            }

            replaceFragment(currentFragment); // Reemplazar el fragmento actual
            return true;
        });

        // Configurar el manejador de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Si el fragmento actual no es el fragmento principal, vuelve al fragmento principal al presionar el botón de retroceso
                if (!(currentFragment instanceof HomeFragment)) {
                    currentFragment = new HomeFragment();
                    replaceFragment(currentFragment);
                    binding.bottomNavigationView.setSelectedItemId(R.id.inicio); // Actualizar la selección en el BottomNavigationView
                } else {
                    // De lo contrario, comportamiento predeterminado del botón de retroceso
                    onBackPressed();
                }
            }
        };

        // Agregar el manejador de retroceso al dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}