package campus.grupo02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @Test
    void testConstructorConDatosValidos() {
        Hotel hotel = new Hotel(1, "Hotel Ritz", 5, "Madrid");
        assertEquals(1, hotel.getId());
        assertEquals("Hotel Ritz", hotel.getNombre());
        assertEquals(5, hotel.getNum_plantas());
        assertEquals("Madrid", hotel.getLocalizacion());
    }

    @Test
    void testConstructorConNombreNulo() {
        Hotel hotel = new Hotel(2, null, 3, "Barcelona");
        assertNull(hotel.getNombre(), "El nombre debería ser null cuando se pasa un valor nulo.");
    }

    @Test
    void testConstructorConNombreVacio() {
        Hotel hotel = new Hotel(3, "", 4, "Sevilla");
        assertEquals("", hotel.getNombre(), "El nombre debería ser una cadena vacía.");
    }

    @Test
    void testConstructorConNumPlantasNegativo() {
        Hotel hotel = new Hotel(4, "Hotel Palace", -3, "Valencia");
        assertEquals(-3, hotel.getNum_plantas(), "El número de plantas debería mantener el valor negativo.");
    }

    @Test
    void testSetAndGetId() {
        Hotel hotel = new Hotel();
        hotel.setId(5);
        assertEquals(5, hotel.getId());
    }

    @Test
    void testSetAndGetNombre() {
        Hotel hotel = new Hotel();
        hotel.setNombre("Gran Hotel");
        assertEquals("Gran Hotel", hotel.getNombre());
    }

    @Test
    void testSetAndGetNumPlantas() {
        Hotel hotel = new Hotel();
        hotel.setNum_plantas(7);
        assertEquals(7, hotel.getNum_plantas());
    }

    @Test
    void testSetAndGetLocalizacion() {
        Hotel hotel = new Hotel();
        hotel.setLocalizacion("Bilbao");
        assertEquals("Bilbao", hotel.getLocalizacion());
    }

    @Test
    void testToString() {
        Hotel hotel = new Hotel(6, "Hotel Miramar", 4, "Málaga");
        String expected = "Hotel{id=6, nombre=Hotel Miramar, num_plantas=4, localizacion=Málaga}";
        assertEquals(expected, hotel.toString());
    }

    @Test
    void testConstructorConNumPlantasMuyNegativo() {
        Hotel hotel = new Hotel(7, "Hotel Lunar", -10, "Marte");
        assertEquals(-10, hotel.getNum_plantas(), "El número de plantas debería mantener el valor negativo extremo.");
    }
}