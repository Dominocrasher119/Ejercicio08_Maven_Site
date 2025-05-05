package campus.grupo02;

import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




//pruebas unitarias:

public class TemporadaTest {

    //CONSTRUCTOR
   @Test
    public void constructorConvertirFechaCorrectamente(){
        Temporada t = new Temporada(1, "Temporada Primavera", "01-03-2025", "30-04-2025");

        assertAll(
                () -> assertEquals("2025-03-01", t.getFecha_inicio()),
                () -> assertEquals("2025-04-30", t.getFecha_fin())
        );
    }


    @Test
    public void constructorIdZero(){
        assertThrows(IllegalArgumentException.class,
                () -> new Temporada(0, "Nombre", "2025-01-01", "2025-01-02"));
    }

    @Test
    public void constructorNombreVacio(){
       assertThrows(IllegalArgumentException.class,
               () -> new Temporada(1, "", "2025-01-01", "2025-01-02"));
    }



    @Test
    void constructorFechaInicioVacia() {
        assertThrows(IllegalArgumentException.class,
                () -> new Temporada(1, "Nombre", "", "2025-01-02"));
    }

    @Test
    void constructorFechaFinAnteriorFechaInicio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Temporada(1, "Nombre", "2025-01-10", "2025-01-01"));
    }

    /* ---------- Setters ---------- */

    @Test
    void setFechaInicioValida() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-01", "2025-02-10");
        assertDoesNotThrow(() -> t.setFecha_inicio("2025-01-15"));
        assertEquals("2025-01-15", t.getFecha_inicio());
    }

    @Test
    void setFechaFinInvalida() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-10", "2025-01-20");
        assertThrows(IllegalArgumentException.class,
                () -> t.setFecha_fin("2025-01-05"));
    }

    @Test
    void setNombreCaracteresInvalidos() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-10", "2025-01-20");
        assertThrows(IllegalArgumentException.class,
                () -> t.setNombre("Inválido@#"));
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void constructorFechaFinAnteriorFechaInicio() {
        new Temporada(1, "Nombre", "2025-01-10", "2025-01-01");
    }

    @Test
    public void setFechaInicioValida() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-01", "2025-02-10");
        t.setFecha_inicio("2025-01-15");
        assertEquals("2025-01-15", t.getFecha_inicio());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFechaFinInvalida() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-10", "2025-01-20");
        t.setFecha_fin("2025-01-05");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNombreCaracteresInvalidos() {
        Temporada t = new Temporada(1, "Nombre", "2025-01-10", "2025-01-20");
        t.setNombre("Inválido@#");
    }*/

    //No es necesario rrealizar test de los setters y getters de la clase Temporada porque ya los invocamos en los métodos creados aquí!
}
