package campus.grupo02;

import static org.junit.Assert.*;
import org.junit.Test;

public class ClienteTest {
    
    // Tests para el constructor
    
    @Test
    public void testCrearClienteConDatosValidos() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        assertEquals(1, cliente.getId());
        assertEquals("Bernat Marín", cliente.getNombre());
        assertEquals("12345678A", cliente.getIdentificador());
        assertEquals("2006-05-19", cliente.getFechaNacimiento());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConIdInvalido() {
        Cliente.crearCliente(0, "Bernat Marín", "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConNombreNulo() {
        Cliente.crearCliente(1, null, "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConNombreVacio() {
        Cliente.crearCliente(1, "", "12345678A", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConNombreSoloEspacios() {
        Cliente.crearCliente(1, "   ", "12345678A", "2006-05-19");
    }
    
    @Test
    public void testCrearClienteConDNIValido() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        assertEquals("12345678A", cliente.getIdentificador());
    }
    
    @Test
    public void testCrearClienteConNIEValido() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "X1234567L", "2006-05-19"); 
        assertEquals("X1234567L", cliente.getIdentificador());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConDNIInvalido() {
        Cliente.crearCliente(1, "Bernat Marín", "1234567", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConNIEInvalido() {
        Cliente.crearCliente(1, "Bernat Marín", "A1234567L", "2006-05-19");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConFechaNacimientoNula() {
        Cliente.crearCliente(1, "Bernat Marín", "12345678A", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCrearClienteConFechaNacimientoInvalida() {
        Cliente.crearCliente(1, "Bernat Marín", "12345678A", "01/01/2000");
    }
    
    // Tests para getters y setters
    
    @Test
    public void testGetSetId() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setId(2);
        assertEquals(2, cliente.getId());
    }
    
    @Test
    public void testGetSetNombre() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setNombre("María López");
        assertEquals("María López", cliente.getNombre());
    }
    
    @Test
    public void testGetSetIdentificador() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente.setIdentificador("87654321B");
        assertEquals("87654321B", cliente.getIdentificador());
    }
    
    @Test
    public void testGetSetFechaNacimiento() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19"); 
        cliente.setFechaNacimiento("1990-10-15"); // CORREGIDO
        assertEquals("1990-10-15", cliente.getFechaNacimiento()); // CORREGIDO
    }
    
    // Test para toString
    
    @Test
    public void testToString() {
        Cliente cliente = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        String expectedString = "Cliente{id=1, nombre='Bernat Marín', identificador='12345678A', fechaNacimiento='2006-05-19'}";
        assertEquals(expectedString, cliente.toString());
    }
}