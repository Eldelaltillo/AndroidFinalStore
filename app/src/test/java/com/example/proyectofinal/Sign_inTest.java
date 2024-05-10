package com.example.proyectofinal;

import static org.junit.Assert.*;

import com.example.proyectofinal.Activities.SignIn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class Sign_inTest {

    private SignIn signIn;
    @Before
    public void setUp(){
        signIn = new SignIn();
    }

    @Test
    public void singInNotNull(){
        assertNotNull(signIn);
    }

    @Test
    public void isValidEmail_validEmail_returnsTrue() {
        assertTrue(signIn.isValidEmail("example@example.com"));
    }

    @Test
    public void isValidEmail_invalidEmail_returnsFalse() {
        assertFalse(signIn.isValidEmail("invalidEmail"));
    }

    @Test
    public void isValidEmail_emptyEmail_returnsFalse() {
        assertFalse(signIn.isValidEmail(""));
    }

    @Test
    public void isValidPassword_validPassword_returnsTrue() {
        assertTrue(signIn.isValidPassword("password123"));
    }

    @Test
    public void isValidPassword_emptyPassword_returnsFalse() {
        assertFalse(signIn.isValidPassword(""));
    }

}