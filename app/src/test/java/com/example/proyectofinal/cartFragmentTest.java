package com.example.proyectofinal;

import static org.junit.Assert.*;

import com.example.proyectofinal.Fragments.CartFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class cartFragmentTest {
    private CartFragment cartFragment;
    @Before
    public void setUp(){
        cartFragment = new CartFragment();
    }

    @Test
    public void singupNotNull(){
        assertNotNull(cartFragment);
    }

    @Test
    public void formatPrice_integerInput_returnsFormattedString() {
        double price = 100.0;
        String formattedPrice = cartFragment.formatPrice(price);
        assertEquals("100", formattedPrice);
    }

    @Test
    public void formatPrice_decimalInput_returnsFormattedString() {
        double price = 99.99;
        String formattedPrice = cartFragment.formatPrice(price);
        assertEquals("100", formattedPrice);
    }

    @Test
    public void formatPrice_zeroInput_returnsFormattedString() {
        double price = 0.0;
        String formattedPrice = cartFragment.formatPrice(price);
        assertEquals("0", formattedPrice);
    }

    @Test
    public void formatPrice_negativeInput_returnsFormattedString() {
        double price = -50.0;
        String formattedPrice = cartFragment.formatPrice(price);
        assertEquals("-50", formattedPrice);
    }

}