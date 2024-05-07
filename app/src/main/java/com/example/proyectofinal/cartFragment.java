package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Helper.ChangeNumberItemsListener;
import com.example.proyectofinal.Helper.ManagmentCart;

import java.text.NumberFormat;
import java.util.Locale;

public class cartFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private TextView totalFeeTxt,taxTxt, deliveryTxt,totalTxt,emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        initView(view);
        managmentCart = new ManagmentCart(getContext());

        initList();
        calculateCart();

        return view;
    }


    private void initList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( getActivity(), LinearLayoutManager. VERTICAL,  false);
        recyclerView.setLayoutManager (linearLayoutManager);
        adapter=new CartListAdapter(managmentCart.getListCart(), getActivity(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
                checkCartEmpty();
            }
        });

        recyclerView.setAdapter(adapter);
        checkCartEmpty(); // Verificar si el carrito está vacío al iniciar
    }

    private void checkCartEmpty() {
        if (managmentCart.getListCart().isEmpty()) {
            // Si el carrito está vacío, mostrar el mensaje de carrito vacío y ocultar el ScrollView
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            // Si el carrito no está vacío, ocultar el mensaje de carrito vacío y mostrar el ScrollView
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {


        double itemTotal=Math.round(managmentCart.getTotalFee()*100)/100;

        // Definir el costo de envío predeterminado
        double delivery = 6000;

        // Verificar si el total es mayor que 2000000
        if (itemTotal > 2000000) {
            // Si es mayor, establecer el costo de envío a 0
            delivery = 0;
        } else {
            // Si es menor, establecer el costo de envío a 10000
            delivery = 10000;
        }

        double total = Math.round((managmentCart.getTotalFee()+delivery)*100)/100;

        String formattedItemTotal = formatPrice(itemTotal);
        String formattedDelivery = formatPrice(delivery);
        String formattedTotal = formatPrice(total);

        totalFeeTxt.setText("$"+formattedItemTotal);
        deliveryTxt.setText("$"+formattedDelivery);
        totalTxt.setText("$"+formattedTotal);
    }


    private void initView(View view) {
        totalFeeTxt=view.findViewById(R.id.totalFeeTxt);
        deliveryTxt=view.findViewById(R.id.deliveryTxt);
        totalTxt=view.findViewById(R.id.totalTxt);
        recyclerView=view.findViewById(R.id.view3);
        scrollView=view.findViewById(R.id.scrollView2);
        emptyTxt=view.findViewById(R.id.emptyTxt);
    }

    // Método para formatear precios
    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(0); // Establecer cero decimales
        return formatter.format(price);
    }

}