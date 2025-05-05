package campus.grupo02;

import static org.junit.Assert.*;
import org.junit.Test;

public class ClienteTest {
    
    // Tests para el constructor
    
    @Test
    public void testConstructorConDatosValidos() {
        // Dados valores correctos, el constructor debería crear el objeto sin excepciones
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        assertEquals(1, cliente.getId());
        assertEquals("Bernat Marín", cliente.getNombre());
        assertEquals("12345678A", cliente.getIdentificador());
        assertEquals("2006-05-19", cliente.getFecha_nacimiento());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConIdInvalido() {
        // ID = 0 debería lanzar excepción (debe ser > 0 o -1)
        new Cliente(0, "Bernat Marín", "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConNombreNulo() {
        // Nombre nulo debería lanzar excepción
        new Cliente(1, null, "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConNombreVacio() {
        // Nombre vacío debería lanzar excepción
        new Cliente(1, "", "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConNombreSoloEspacios() {
        // Nombre con solo espacios debería lanzar excepción
        new Cliente(1, "   ", "12345678A", "2006-05-19");
    }
    
    @Test
    public void testConstructorConDNIValido() {
        // DNI válido debería aceptarse
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        assertEquals("12345678A", cliente.getIdentificador());
    }
    
    @Test
    public void testConstructorConNIEValido() {
        // NIE válido debería aceptarse
        Cliente cliente = new Cliente(1, "Bernat Marín", "X1234567L", "2006-05-19");
        assertEquals("X1234567L", cliente.getIdentificador());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConDNIInvalido() {
        // DNI inválido debería lanzar excepción
        new Cliente(1, "Bernat Marín", "1234567", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConNIEInvalido() {
        // NIE inválido debería lanzar excepción
        new Cliente(1, "Bernat Marín", "A1234567L", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConFechaNacimientoNula() {
        // Fecha de nacimiento nula debería lanzar excepción
        new Cliente(1, "Bernat Marín", "12345678A", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorConFechaNacimientoInvalida() {
        // Fecha de nacimiento en formato incorrecto debería lanzar excepción
        new Cliente(1, "Bernat Marín", "12345678A", "01/01/2000");
    }
    
    // Tests para getters y setters
    
    @Test
    public void testGetSetId() {
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setId(2);
        assertEquals(2, cliente.getId());
    }
    
    @Test
    public void testGetSetNombre() {
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setNombre("María López");
        assertEquals("María López", cliente.getNombre());
    }
    
    @Test
    public void testGetSetIdentificador() {
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setIdentificador("87654321B");
        assertEquals("87654321B", cliente.getIdentificador());
    }
    
    @Test
    public void testGetSetFechaNacimiento() {
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setFecha_nacimiento("1990-10-15");
        assertEquals("1990-10-15", cliente.getFecha_nacimiento());
    }
    
    // Test para toString
    
    @Test
    public void testToString() {
        Cliente cliente = new Cliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        String expectedString = "Clientes{id=1, nombre=Bernat Marín, identificador=12345678A, fecha_nacimiento=2006-05-19}";
        assertEquals(expectedString, cliente.toString());
    }
}