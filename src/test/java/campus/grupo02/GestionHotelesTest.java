package campus.grupo02;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestionHotelesTest {

    // Instancias que se usarán en las pruebas
    private GestionHoteles gestionHoteles;
    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    // Método que se ejecuta antes de cada prueba para inicializar los objetos
    @BeforeEach
    void setUp() {
        gestionHoteles = new GestionHoteles(); // Crea una nueva instancia de la clase a probar
        hotel1 = new Hotel(1, "Hotel Sol", 5, "Playa del Sol"); // Hotel con ID 1
        hotel2 = new Hotel(2, "Hotel Luna", 10, "Montaña Luna"); // Hotel con ID 2
        hotel3 = new Hotel(3, "Hotel Estrella", 7, "Ciudad Estrella"); // Hotel con ID 3
    }

    // Prueba que el constructor inicializa correctamente una lista vacía
    @Test
    void testConstructor() {
        assertNotNull(gestionHoteles.getAll()); // Verifica que la lista no sea null
        assertTrue(gestionHoteles.isEmpty()); // Verifica que esté vacía
        assertEquals(0, gestionHoteles.size()); // Verifica que el tamaño sea 0
    }

    // Prueba añadir un hotel nuevo
    @Test
    void testAddHotel() {
        assertTrue(gestionHoteles.add(hotel1)); // Añade hotel1 y espera que devuelva true
        assertEquals(1, gestionHoteles.size()); // Verifica que hay 1 hotel
        assertFalse(gestionHoteles.isEmpty()); // Verifica que ya no esté vacía
        assertEquals(hotel1, gestionHoteles.getById(1)); // Verifica que el hotel añadido se recupere correctamente
    }

    // Prueba que no se puede añadir un hotel duplicado (por ID)
    @Test
    void testAddHotelDuplicado() {
        gestionHoteles.add(hotel1); // Añade el hotel
        assertFalse(gestionHoteles.add(hotel1)); // Intenta añadirlo otra vez, debería fallar
        assertEquals(1, gestionHoteles.size()); // El tamaño debe seguir siendo 1
    }

    // Prueba la búsqueda por ID de hoteles existentes
    @Test
    void testGetHotelPorIdExistente() {
        gestionHoteles.add(hotel1); // Añade hotel1
        gestionHoteles.add(hotel2); // Añade hotel2
        assertEquals(hotel1, gestionHoteles.getById(1)); // Verifica que se obtiene hotel1 por su ID
        assertEquals(hotel2, gestionHoteles.getById(2)); // Verifica que se obtiene hotel2 por su ID
    }

    // Test para verificar que se maneja correctamente una posición inválida en la
    // lista. Se prueba con posiciones negativas y fuera de rango.

    @Test
    public void testObtenerPorPosicionInvalida() {
        gestionHoteles.add(hotel1);

        assertNull(gestionHoteles.get(-1), "Una posición negativa debe devolver null");
        assertNull(gestionHoteles.get(1), "Una posición fuera de rango debe devolver null");
    }

    // Prueba reemplazar un hotel existente (misma ID)
    @Test
    void testReplaceHotelExistente() {
        gestionHoteles.add(hotel1); // Añade hotel1
        Hotel hotelModificado = new Hotel(1, "Hotel Sol Nuevo", 6, "Playa del Sol Nueva"); // Hotel con mismo ID pero
                                                                                           // datos distintos
        assertTrue(gestionHoteles.replace(hotelModificado)); // Reemplaza el hotel
        assertEquals(hotelModificado, gestionHoteles.getById(1)); // Verifica que el hotel ha sido reemplazado
                                                                  // correctamente
        assertEquals("Hotel Sol Nuevo", gestionHoteles.getById(1).getNombre()); // Verifica nuevo nombre
        assertEquals(1, gestionHoteles.size()); // Verifica que el tamaño no cambie
    }

    // Prueba eliminar un hotel por ID existente
    @Test
    void testRemoveHotelPorIdExistente() {
        gestionHoteles.add(hotel1); // Añade hotel1
        gestionHoteles.add(hotel2); // Añade hotel2
        assertTrue(gestionHoteles.remove(1)); // Elimina hotel1
        assertEquals(1, gestionHoteles.size()); // Solo debe quedar uno
        assertNull(gestionHoteles.getById(1)); // Ya no debe existir el hotel1
        assertEquals(hotel2, gestionHoteles.getById(2)); // hotel2 debe seguir existiendo
    }

    // Test para verificar que no se puede eliminar un hotel que no existe en la
    // lista.

    @Test
    public void testEliminarHotelPorObjetoNoExistente() {
        gestionHoteles.add(hotel1);

        Hotel hotelNoExistente = new Hotel(99, "Hotel Fantasma", 2, "Madrid");
        boolean resultado = gestionHoteles.remove(hotelNoExistente);

        assertFalse(resultado, "No se debería poder eliminar un hotel inexistente");
        assertEquals(1, gestionHoteles.size(), "El tamaño de la lista no debe cambiar");
    }
    // Test para verificar que se obtiene correctamente la lista de todos los
    // hoteles.

    @Test
    public void testObtenerTodosLosHoteles() {
        gestionHoteles.add(hotel1);
        gestionHoteles.add(hotel2);

        ArrayList<Hotel> listaHoteles = gestionHoteles.getAll();

        assertEquals(2, listaHoteles.size(), "El tamaño de la lista obtenida debe ser 2");
        assertEquals(hotel1.getId(), listaHoteles.get(0).getId(),
                "El ID del primer hotel debe coincidir con el esperado");
        assertEquals(hotel2.getId(), listaHoteles.get(1).getId(),
                "El ID del segundo hotel debe coincidir con el esperado");
    }

    // Prueba búsqueda por nombre parcial
    @Test
    void testFindByNombreParcialCaseInsensitive() {
        gestionHoteles.add(hotel1); // Añade hotel1
        gestionHoteles.add(hotel2); // Añade hotel2
        gestionHoteles.add(hotel3); // Añade hotel3

        // Busca "hotel", debería encontrar los 3
        ArrayList<Hotel> resultado = gestionHoteles.findByNombre("hotel");
        assertEquals(3, resultado.size()); // Se esperan los 3
        assertTrue(resultado.contains(hotel1));
        assertTrue(resultado.contains(hotel2));
        assertTrue(resultado.contains(hotel3));

        // Busca "lUnA", debería encontrar solo hotel2
        resultado = gestionHoteles.findByNombre("lUnA");
        assertEquals(1, resultado.size()); // Solo uno coincide
        assertEquals(hotel2, resultado.get(0));
    }

    // Prueba borrar todos los hoteles
    @Test
    void testClear() {
        gestionHoteles.add(hotel1); // Añade hotel1
        gestionHoteles.add(hotel2); // Añade hotel2
        assertFalse(gestionHoteles.isEmpty()); // Verifica que no esté vacía
        gestionHoteles.clear(); // Borra todo
        assertTrue(gestionHoteles.isEmpty()); // Verifica que esté vacía
        assertEquals(0, gestionHoteles.size()); // Verifica tamaño 0
        assertNull(gestionHoteles.getById(1)); // hotel1 ya no debe existir
        assertNull(gestionHoteles.getById(2)); // hotel2 ya no debe existir
    }
}
