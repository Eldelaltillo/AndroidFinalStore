package com.example.proyectofinal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Context;

import com.example.proyectofinal.Adapters.CartListAdapter;
import com.example.proyectofinal.Helper.PopularDomain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
@RunWith(RobolectricTestRunner.class)
public class CartListAdapterTest {

    @Test
    public void formatPrice_ReturnsFormattedPrice() {
        // Crear un contexto ficticio utilizando Mockito
        Context context = mock(Context.class);

        // Crear una lista ficticia de PopularDomain
        ArrayList<PopularDomain> dummyList = new ArrayList<>();

        // Crear una instancia de CartListAdapter con el contexto ficticio y la lista ficticia
        CartListAdapter cartListAdapter = new CartListAdapter(dummyList, context, null);

        double price = 10.50;
        String formattedPrice = cartListAdapter.formatPrice(price);
        String expectedFormattedPrice = "$11"; // El precio se redondea a 11
        assertEquals(expectedFormattedPrice, formattedPrice);
    }

    @Test
    public void formatTotalPrice_ReturnsFormattedTotalPrice() {
        // Crear un contexto ficticio utilizando Mockito
        Context context = mock(Context.class);

        // Crear una lista ficticia de PopularDomain
        ArrayList<PopularDomain> dummyList = new ArrayList<>();

        // Crear una instancia de CartListAdapter con el contexto ficticio y la lista ficticia
        CartListAdapter cartListAdapter = new CartListAdapter(dummyList, context, null);

        double price = 10.50;
        int quantity = 3;
        String formattedTotalPrice = cartListAdapter.formatTotalPrice(price, quantity);
        String expectedFormattedTotalPrice = "$32"; // 10.50 * 3 = 31
        assertEquals(expectedFormattedTotalPrice, formattedTotalPrice);
    }
    @Test
    public void formatTotalPrice_ReturnsFormattedTotalPrice2() {
        // Crear un contexto ficticio utilizando Mockito
        Context context = mock(Context.class);

        // Crear una lista ficticia de PopularDomain
        ArrayList<PopularDomain> dummyList = new ArrayList<>();

        // Crear una instancia de CartListAdapter con el contexto ficticio y la lista ficticia
        CartListAdapter cartListAdapter = new CartListAdapter(dummyList, context, null);

        double price = 10.40;
        int quantity = 3;
        String formattedTotalPrice2 = cartListAdapter.formatTotalPrice(price, quantity);
        String expectedFormattedTotalPrice2 = "$31";
        assertEquals(expectedFormattedTotalPrice2, formattedTotalPrice2);
    }

}