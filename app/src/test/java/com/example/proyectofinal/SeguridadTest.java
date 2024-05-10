package com.example.proyectofinal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.proyectofinal.Activities.Seguridad;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SeguridadTest {

    private Seguridad seguridad;
    @Before
    public void setUp(){
        seguridad = new Seguridad();
    }

    @Test
    public void singupNotNull(){
        assertNotNull(seguridad);
    }
    @Test
    public void verificarContraseñaNoVacia_ContraseñaVacia_RetornaFalse() {
        boolean resultado = seguridad.verificarContraseñaNoVacia("");
        assertFalse(resultado);
    }

    @Test
    public void verificarContraseñaNoVacia_ContraseñaNoVacia_RetornaTrue() {
        boolean resultado = seguridad.verificarContraseñaNoVacia("contraseña");
        assertTrue(resultado);
    }
}