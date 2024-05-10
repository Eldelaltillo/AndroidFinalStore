package com.example.proyectofinal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.proyectofinal.Activities.SignUp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Sign_upTest {

    private SignUp singup;
    @Before
    public void setUp(){
        singup = new SignUp();
    }

    @Test
    public void singupNotNull(){
        assertNotNull(singup);
    }

    @Test
    public void add() {
        assertEquals(7,singup.add(5,2));
    }

    @Test
    public void validateName_NotEmpty() {
        assertTrue(singup.validateName("John")); // Aquí puedes colocar un nombre que no esté vacío
    }

    @Test
    public void validateName_Empty() {
        assertFalse(singup.validateName("")); // Aquí pasamos una cadena vacía para asegurarnos de que devuelve falso
    }

    @Test
    public void validateEmail_Valid() {
        assertTrue(singup.validateEmail("example@example.com"));
    }

    @Test
    public void validateEmail_Invalid() {
        assertFalse(singup.validateEmail("invalidemail")); // Esto no es una dirección de correo electrónico válida
    }

    @Test
    public void validateUsername_NotEmpty() {
        assertTrue(singup.validateUsername("username"));
    }

    @Test
    public void validateUsername_Empty() {
        assertFalse(singup.validateUsername(""));
    }

    @Test
    public void validatePassword_NotEmpty() {
        assertTrue(singup.validatePassword("password"));
    }

    @Test
    public void validatePassword_Empty() {
        assertFalse(singup.validatePassword(""));
    }
}