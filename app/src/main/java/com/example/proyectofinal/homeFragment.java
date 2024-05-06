package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class homeFragment extends Fragment {
    private RecyclerView.Adapter adapterPopular;
    private RecyclerView recyclerViewPopular;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initRecyclerview(view);

        return view;
    }

    private void initRecyclerview(View view) {
        ArrayList<PopularDomain> items = new ArrayList<>();

        items.add(new PopularDomain("producto", "hola, esto es una prueba", "1 TB", "16 GB", "RTX 4090", "pic1", 250, -10, 3000000));
        items.add(new PopularDomain("producto2", "hola, esto es una prueba", "2 TB", "8 GB", "RTX 3020", "pic1", 250, -20, 2500000));
        items.add(new PopularDomain("producto3", "hola, esto es una prueba", "250 GB", "6 GB", "RTX 1060", "pic1", 250, 0, 9000000));
        items.add(new PopularDomain("producto4", "hola, esto es una prueba", "1 TB", "16 GB", "RTX 2050", "pic1", 250, -50, 1500000));
        items.add(new PopularDomain("Esta es la prueba para ver como se ve el nombre de un producto largo", "hola, esto es una prueba", "2 TB", "8 GB", "RTX 3050", "pic1", 250, -25, 30000000));
        items.add(new PopularDomain("producto6", "hola, esto es una prueba", "250 GB", "16 GB", "RTX 4060", "pic1", 250, -30, 2999999));
        items.add(new PopularDomain("producto7", "hola, esto es una prueba", "1 TB", "32 GB", "RTX 3060", "pic1", 250, -40, 1050099));
        items.add(new PopularDomain("producto8", "hola, esto es una prueba", "250 GB", "8 GB", "RTX 1050", "pic1", 250, -50, 2700000));
        items.add(new PopularDomain("producto9", "hola, esto es una prueba", "2 TB", "32 GB", "RTX 1060", "pic1", 250, -20, 7000000));
        items.add(new PopularDomain("producto10", "hola, esto es una prueba", "250 GB", "16 GB", "RTX 3060", "pic1", 250, -30, 35900000));
        items.add(new PopularDomain("producto11", "hola, esto es una prueba", "2 TB", "16 GB", "RTX 1000", "pic1", 250, -10, 4000000));
        items.add(new PopularDomain("producto", "hola, esto es una prueba", "1 TB", "16 GB", "RTX 4090", "pic1", 250, -10, 3000000));
        items.add(new PopularDomain("producto2", "hola, esto es una prueba", "2 TB", "8 GB", "RTX 3020", "pic1", 250, -20, 2500000));
        items.add(new PopularDomain("producto3", "hola, esto es una prueba", "250 GB", "6 GB", "RTX 1060", "pic1", 250, 0, 9000000));
        items.add(new PopularDomain("producto4", "hola, esto es una prueba", "1 TB", "16 GB", "RTX 2050", "pic1", 250, -50, 1500000));
        items.add(new PopularDomain("Esta es la prueba para ver como se ve el nombre de un producto largo", "hola, esto es una prueba", "2 TB", "8 GB", "RTX 3050", "pic1", 250, -25, 30000000));
        items.add(new PopularDomain("producto6", "hola, esto es una prueba", "250 GB", "16 GB", "RTX 4060", "pic1", 250, -30, 2999999));
        items.add(new PopularDomain("producto7", "hola, esto es una prueba", "1 TB", "32 GB", "RTX 3060", "pic1", 250, -40, 1050099));
        items.add(new PopularDomain("producto8", "hola, esto es una prueba", "250 GB", "8 GB", "RTX 1050", "pic1", 250, -50, 2700000));
        items.add(new PopularDomain("producto9", "hola, esto es una prueba", "2 TB", "32 GB", "RTX 1060", "pic1", 250, -20, 7000000));
        items.add(new PopularDomain("producto10", "hola, esto es una prueba", "250 GB", "16 GB", "RTX 3060", "pic1", 250, -30, 35900000));
        items.add(new PopularDomain("producto11", "hola, esto es una prueba", "2 TB", "16 GB", "RTX 1000", "pic1", 250, -10, 4000000));


        recyclerViewPopular = view.findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        adapterPopular = new PopularListAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);
    }

}







