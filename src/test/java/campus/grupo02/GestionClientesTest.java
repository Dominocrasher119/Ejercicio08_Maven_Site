package campus.grupo02;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestionClientesTest {
    
    private GestionClientes gestion;
    private Cliente cliente1;
    private Cliente cliente2;
    
    @BeforeEach
    public void setUp() {
        // Creo una instancia de GestionClientes y dos clientes para usar en los tests inventados por mi
        gestion = new GestionClientes();
        cliente1 = Cliente.crearCliente(1, "Bernat Marín", "12345678A", "2006-05-19");
        cliente2 = Cliente.crearCliente(2, "Gerard Parareda", "98243553Q", "2006-01-01");
    }
    
    @Test
    public void testConstructor() {
        // Hacemos un test del constructor de GestionClientes para ver que se inicializa correctamente y que la lista de clientes está vacía
        GestionClientes g = new GestionClientes();
        assertEquals(0, g.size());
        assertTrue(g.isEmpty());
    }
    
    @Test
    public void testGetPosicionValida() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        
        // Obtenemos el cliente en la posición 0 (debería ser cliente1) 
        Cliente clienteObtenido = gestion.get(0);
        assertEquals(cliente1.getId(), clienteObtenido.getId());
        assertEquals(cliente1.getNombre(), clienteObtenido.getNombre());
    }
    
    @Test
    // Test para comprobar que la posición es válida y que devuelve el cliente correcto
    public void testGetPosicionInvalida() {
        gestion.add(cliente1);
        
        // Posición negativa debería devolver null
        assertNull(gestion.get(-1));
        
        // Posición fuera de rango
        assertNull(gestion.get(1));
    }
    
    @Test
    // Test para comprobar que la posición es válida y que devuelve el cliente correcto
    public void testGetByIdExistente() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        
        // Obtenemos el cliente con ID=2 (debería ser cliente2)
        Cliente clienteObtenido = gestion.getById(2);
        assertNotNull(clienteObtenido);
        assertEquals(cliente2.getId(), clienteObtenido.getId());
        assertEquals(cliente2.getNombre(), clienteObtenido.getNombre());
    }
    
    @Test
    // Test para comprobar que la posición es válida y que devuelve el cliente correcto
    public void testGetByIdNoExistente() {
        // Añadimos un cliente a la lista de gestión
        gestion.add(cliente1);
        
        // Intentamos obtener un cliente con un ID que no existe en mi test (7)
        Cliente clienteObtenido = gestion.getById(7);
        assertNull(clienteObtenido);
    }
    
    @Test
    // Test para comprobar que la posición es válida y que devuelve el cliente correcto
    public void testAddNuevoCliente() {
        // Añadir un cliente nuevo a la lista de gestión y comprobar que se añade correctamente
        boolean resultado = gestion.add(cliente1);
        
        //Si el cliente se añade correctamente, el resultado debe ser true
        assertTrue(resultado);
        assertEquals(1, gestion.size());
        // Comprobar que el cliente añadido es el correcto y que equivale al cliente1
        assertEquals(cliente1.getId(), gestion.get(0).getId());
    }
    
    @Test
    // Test para comprobar que la posición es válida y que devuelve el cliente correcto
    public void testAddClienteDuplicado() {
        // Añadir un cliente a la lista de gestión
        gestion.add(cliente1);
        
        // Intentar añadir un cliente con el mismo ID
        // Usar el método crearCliente
        Cliente clienteDuplicado = Cliente.crearCliente(1, "Bad Bunny", "98765432A", "2000-03-10");
        // Creamos un boolean para poder saber si se ha añadido o no el cliente duplicado
        boolean resultado = gestion.add(clienteDuplicado);
        
        // Si el cliente duplicado no se añade, el resultado debe ser false
        assertFalse(resultado);
        assertEquals(1, gestion.size()); // No debe haber aumentado el tamaño el arraylist
    }
    
    @Test
    // Test para remplazar un cliente existente
    public void testReplaceClienteExistente() {
        // Añadir un cliente a la lista de gestión
        gestion.add(cliente1);
        
        // Cliente con mismo ID pero datos diferentes
        // Usar el método crearCliente
        Cliente clienteModificado = Cliente.crearCliente(1, "Bernat Marín Modificado", "12345678A", "2006-05-19");
        // Remplazamos el cliente (Bernat Marín) por el cliente modificado (Bernat Marín Modificado)
        // Creamos un boolean para poder saber si se ha añadido o no el cliente duplicado
        boolean resultado = gestion.replace(clienteModificado);
        
        // Si el cliente se remplaza correctamente, el resultado debe ser true
        assertTrue(resultado);
        // Verificamos que coincide el cliente modificado con el cliente que tenemos en la lista de gestión
        assertEquals(1, gestion.size());
        // El tamaño no debe cambiar
        assertEquals("Bernat Marín Modificado", gestion.getById(1).getNombre());
    }
    
    @Test
    // Test para remplazar un cliente que no existe
    public void testReplaceClienteNoExistente() {
        // Añadimos cliente1 a la lista de gestión
        gestion.add(cliente1);
        // Ponemos un boolean para verificar si se remplaza o no el cliente
        boolean resultado = gestion.replace(cliente2); // ID=2 no existe aún
        
        // Si el cliente no se encuentra, el resultado debe ser false
        assertFalse(resultado);
    }
    
    @Test
    // Test para eliminar el cliente de la lista de gestión
    public void testRemoveClienteObjeto() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        
        // Añadimos un boolean para saber si se ha eliminado o no el cliente
        boolean resultado = gestion.remove(cliente1);
        
        // En caso de que el boolean sea true, significa que se ha eliminado correctamente
        assertTrue(resultado);
        // Si el cliente se ha eliminado correctamente, el tamaño de la lista de gestión debe ser 1
        assertEquals(1, gestion.size());
        // Comprobamos que el cliente eliminado ya no está en la lista de gestión con la id=1, si se ha eliminado correctamente, el cliente con id=1 debe ser null
        assertNull(gestion.getById(1));
    }
    
    @Test
    // Test para eliminar un cliente que no existe
    public void testRemoveClienteObjetoNoExistente() {
        // Añadimos un cliente a la lista de gestión
        gestion.add(cliente1);
        
        // Creamos un objecto cliente que no existe en la lista de gestión
        // Usar el método crearCliente
        Cliente clienteNoExistente = Cliente.crearCliente(99, "No Existe", "11111111A", "2001-01-01");
        // Generamos un boolean para saber si se ha eliminado o no el cliente
        boolean resultado = gestion.remove(clienteNoExistente);
        
        // El resultado esperado devería ser false, ya que el cliente no existe en la lista de gestión
        assertFalse(resultado);
        // El tamño de la lista deberia ser el mismo que antes de eliminar el cliente, ya que no se ha eliminado
        assertEquals(1, gestion.size());
    }
    
    @Test
    // Test para eliminar un cliente por su ID
    public void testRemoveClientePorId() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        
        // Creamos un boolean para saber si se ha eliminado o no el cliente
        boolean resultado = gestion.remove(1); // Eliminar cliente con ID=1
        
        // El resultado esperado devería ser true, ya que el cliente existe en la lista de gestión
        assertTrue(resultado);
        // Si el cliente se ha eliminado correctamente, el tamaño de la lista de gestión debe ser 1
        assertEquals(1, gestion.size());
        // Comprobamos que el cliente eliminado ya no está en la lista de gestión con la id=1, si se ha eliminado correctamente, el cliente con id=1 debe ser null
        assertNull(gestion.getById(1));
    }
    
    @Test
    // Test para eliminar cliente por ID que no existe
    public void testRemoveClientePorIdNoExistente() {
        // Añadimos un cliente a la lista de gestión
        gestion.add(cliente1);
        
        boolean resultado = gestion.remove(7); // El ID=7 no existente
        
        // El boolean deveria ser false, ya que el cliente no existe en la lista de gestión
        assertFalse(resultado);
        // El tamaño de la lista debería ser el mismo que antes de eliminar el cliente, ya que no se ha eliminado
        assertEquals(1, gestion.size());
    }
    
    @Test
    // Test para coger todos los clientes de la lista de gestión
    public void testGetAll() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        
        // Creamos en un arraylist una lista de clientes con el método getAll()
        ArrayList<Cliente> listaClientes = gestion.getAll();
        
        // Comprobamos que el tamaño de la lista de clientes es el esperado (2)
        assertEquals(2, listaClientes.size());
        // Comprovamos que equivale la id que cogemos del arrraylist a la id del cliente que tenemos en la lista de gestión
        assertEquals(cliente1.getId(), listaClientes.get(0).getId());
        assertEquals(cliente2.getId(), listaClientes.get(1).getId());
        
        // Limpiamos el arraylist
        listaClientes.clear();
        // Comprobamos que el tamaño de la lista de clientes es 0
        assertEquals(0, listaClientes.size());
        // Comprobamos que el tamaño de la lista de gestión es 2, ya que no hemos eliminado nada
        assertEquals(2, gestion.size());
    }
    
    @Test
    // Test para comprobar el tamaño de la lista de gestión
    public void testSize() {
        // Comprobamos que el tamaño de la lista de gestión es 0 al principio porque no hemos añadido nada
        assertEquals(0, gestion.size());
        
        // Añadimos un cliente a la lista de gestión
        gestion.add(cliente1);
        // El tamaño de la lista de gestión debería ser 1
        assertEquals(1, gestion.size());
        
        // Añadimos otro cliente a la lista de gestión
        gestion.add(cliente2);
        // El tamaño de la lista de gestión debería ser 2
        assertEquals(2, gestion.size());
        
        // Eliminamos el cliente 1 con la ID=1 de la lista de gestión
        gestion.remove(1);
        // El tamaño de la lista de gestión debería ser 1
        assertEquals(1, gestion.size());
    }
    
    @Test
    // Test para comprobar si la lista de gestión está vacía
    public void testIsEmpty() {
        // Comprobamos que la lista de gestión está vacía al principio
        assertTrue(gestion.isEmpty());
        
        // Añadimos un cliente a la lista de gestión
        gestion.add(cliente1);
        // Debería ser false, ya que hemos añadido un cliente
        assertFalse(gestion.isEmpty());
        
        // Eliminamos el cliente 1 con la ID=1 de la lista de gestión
        gestion.remove(1);
        // Debería ser true, ya que hemos eliminado el cliente y la lista de gestión está vacía
        assertTrue(gestion.isEmpty());
    }
    
    @Test
    // Test para comprobar la búsqueda de clientes por nombre
    public void testFindByNombre() {
        // Insertamos los 2 clientes en la lista de gestión
        gestion.add(cliente1); // Bernat Marín
        gestion.add(cliente2); // Gerard Parareda
        // Usar el método  crearCliente
        gestion.add(Cliente.crearCliente(3, "Bernat López", "22222222B", "1995-05-05")); // Añado un cliente más para hacer más pruebas
        
        // Búsqueda que debe encontrar coincidencias
        // Debería encontrar 2 coincidencias: "Bernat Marín" y "Bernat López"
        ArrayList<Cliente> resultados = gestion.findByNombre("Bernat");
        // El resultado debería ser 2, ya que hay 2 clientes con el nombre "Bernat"
        assertEquals(2, resultados.size());
        

        // Búsqueda que debe encontrar una coincidencia exacta "Gerard Parareda"
        resultados = gestion.findByNombre("Gerard Parareda");
        // Debería encontrar 1 coincidencia
        assertEquals(1, resultados.size());
        // Comprobamos que el cliente encontrado es el correcto
        assertEquals(2, resultados.get(0).getId());
        
        // Búsqueda que no debe encontrar coincidencias
        resultados = gestion.findByNombre("Pedro");
        // Debería ser 0, ya que no hay clientes con ese nombre
        assertEquals(0, resultados.size());
        
        // Búsqueda que debe ser insensible a mayúsculas/minúsculas
        // Búsqueda con "BeRNaT" debería encontrar 2 coincidencias
        // Debería encontrar 2 coincidencias: "Bernat Marín" y "Bernat López"
        resultados = gestion.findByNombre("BeRNaT");
        // El resultado debería ser 2, ya que hay 2 clientes con el nombre "Bernat"
        assertEquals(2, resultados.size());
    }
    
    @Test
    // Test para comprobar la búsqueda de clientes por identificador (DNI/NIE)
    public void testFindByIdentificador() {
        // Insertamos los 2 clientes en la lista de gestión y estos son sus DNIs
        // DNI: 12345678A y DNI: 98243553Q
        gestion.add(cliente1); // Bernat Marín
        gestion.add(cliente2); // Gerard Parareda
        
        // Búsqueda que debe encontrar coincidencia
        Cliente resultado = gestion.findByIdentificador("12345678A");
        // La busqueda no puede ser null, ya que el cliente existe
        assertNotNull(resultado);
        // Comprobamos que el cliente encontrado es el correcto con la ID=1
        assertEquals(1, resultado.getId());
        
        // Búsqueda que no debe encontrar coincidencia
        // Buscamos un DNI que no existe en la lista de gestión
        resultado = gestion.findByIdentificador("99999999Y");
        // La busqueda debe ser null, ya que el cliente no existe
        assertNull(resultado);
    }
    
    @Test
    // Test para comprobar la eliminación de todos los clientes
    public void testClear() {
        // Añadimos dos clientes a la lista de gestión
        gestion.add(cliente1);
        gestion.add(cliente2);
        // Verificamos que el tamaño de la lista de gestión es 2
        assertEquals(2, gestion.size());
        
        // Limpiamos la lista de gestión
        gestion.clear();
        
        // Verificamos que el tamaño de la lista de gestión es 0
        assertEquals(0, gestion.size());
        // Verificamos que la lista de gestión está vacía
        assertTrue(gestion.isEmpty());
    }
}