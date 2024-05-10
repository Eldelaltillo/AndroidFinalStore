package com.example.proyectofinal.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.Helper.PopularDomain;
import com.example.proyectofinal.Adapters.PopularListAdapter;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String URL_PRODUCTS = "https://androidedyleo2024.000webhostapp.com/registrosStore.php";

    private ArrayList<PopularDomain> productList;
    private RecyclerView recyclerView;
    private PopularListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productList = new ArrayList<>();
        adapter = new PopularListAdapter(productList);
        recyclerView.setAdapter(adapter);

        loadProducts();

        return view;
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new PopularDomain(
                                        product.getString("title"),
                                        product.getString("description"),
                                        product.getString("Storage"),
                                        product.getString("RAM"),
                                        product.getString("GPU"),
                                        product.getString("picUrl"),
                                        product.getInt("review"),
                                        product.getDouble("score"),
                                        product.getDouble("price"),
                                        product.getString("categoria")
                                ));
                            }
                            adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Error", error.toString());

                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    //Aca se dividen ambos codigo

/*
    //Codigo base de datos en la nube con API
    private static final String URL_PRODUCTS = "http://192.168.20.137/ApiProyectoFinal/registros.php";

    private List<PopularDomain> productList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productList = new ArrayList<>();

        loadProducts();

        return view;
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new PopularDomain(
                                        product.getString("title"),
                                        product.getString("description"),
                                        product.getString("Storage"),
                                        product.getString("RAM"),
                                        product.getString("GPU"),
                                        product.getString("picUrl"),
                                        product.getInt("review"),
                                        product.getDouble("score"),
                                        product.getDouble("price")
                                ));
                            }
                            PopularListAdapter adapter = new PopularListAdapter(getContext(), productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Error", error.toString());

                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

*/

// Aca se separan ambos codigos



/*
    //Codigo sin base de datos

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


        recyclerViewPopular = view.findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        adapterPopular = new PopularListAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);
    }*/

}







