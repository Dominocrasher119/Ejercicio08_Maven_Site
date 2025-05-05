package campus.grupo02;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GestionTemporadasTest {

    private GestionTemporadas gestion;

    //En éste método lo que queremos es, que se cree una nueva instancia de GestionTemporadas pero limpia. Así cada vez que se realice una prueba, empezará sin datos "residuales" de pruebas anteriores...
    //Vamos, un puntazo tener esto en cuenta... ^_^
    @Before
    public void setUp(){
        gestion = new GestionTemporadas();
    }

    //Este método se encargará de comprobar el estado incial de la clase Gestora:
    //Es util para asegurarnos de que el constructor dejará la lista interna totalmente vacía.
    @Test
    public void testEmptyAndSizeInicial(){
        //tendría que devolver true porque todabía no hemos añadido nada...
        assertTrue("Tiene que estar vacío al principio", gestion.isEmpty());
        //El campos "size" tiene que valer 0 porque no hay nada aún!
        assertEquals("El campo 'size' tiene que ser 0 al inicio", 0, gestion.size());
    }

    //Este método nos viene bien para prevenir un índice fuera de rango en el caso de que al usuario se le vaya completmente la olla y pida una posición como -1.
    //queremos que la posicion 0 devuelva un objeto y que compruebe que -1 devuelva "null":
    @Test
    public void testGetPorPosicion(){
        gestion.add(new Temporada(1, "T1", "2025-01-01", "2025-02-01"));
        //la posición 0 está dentro del rango permitido :D
        assertNotNull("get(0) válido no puede ser null", gestion.get(0));
        //-1 ya no... :(
        assertNull("get(-1) debe ser null", gestion.get(-1));
        assertNull("get(size) debe ser null", gestion.get(gestion.size()));
    }

    //Este método se encargará de que se añaden entradas correctamente y que rechaze cosas duplicadas.
    //Es una forma de asegurar la lógica de inserción y actualización por clave primaria.
    @Test
    public void TestAñadeRemplazaYGetById(){
        Temporada principal = new Temporada(2, "Primavera", "2025-03-01", "2025-05-31");
        //con "add" añadimos nueva temporada:
        assertTrue(gestion.add(principal));

        Temporada modificada = new Temporada(2, "Primaverita", "2025-03-15", "2025-06-01");
        //"replace" solo devolverá true, si reconoce una id exactamente igual:
        assertTrue("replace tiene que devolver true si existe", gestion.replace(modificada));
        //"getById" permite recuperar la temporada añadida o sustituida gracias a su id!
        assertEquals("El nombre tiene que actualizarse", "Primaverita", gestion.getById(2).getNombre());

        Temporada erronea = new Temporada(99, "Ninguna", "2025-01-01", "2025-01-02");
        assertFalse("replace tiene que devolver false si no existe", gestion.replace(erronea));
    }

    //Este método permite asegurarse de que las 2 formas que vamos a usar para borrar registros, funcionen correctamente (por id y por objeto):
    @Test
    public void TestEliminarSobrecarga(){
        //primero añadimos una temporada:
        Temporada t = new Temporada(3, "Verano", "2025-06-01", "2025-08-31");
        gestion.add(t);

        //hacemos un remove (id) de la temporada que acabamos de meter:
        assertTrue("remove(id) debe eliminar", gestion.remove(3));
        //por lo tanto, dicho id tiene que quedar a null (porque ya no existe!)
        assertNull(gestion.getById(3));

        //volvemos a añadir (por objeto) y eliminar:
        gestion.add(t);
        assertTrue("remove(obj) debe eliminar", gestion.remove(t));
        //y otra vez la comprobacion por id:
        assertNull(gestion.getById(3));
    }

    //Este método evita fugas de encapsulación (que nadie de fuera de la clase pueda acceder al estado interno) **tengo que preguntarle a Carmen sobre esto...**. La lista interna no debe poder alterarse desde fuera.
    //Lo que nos interesa aqui es que "getAll()" nos devuelva una copia de la lista interna:
    @Test
    public void testGetAllIndependiente() {
        //primero añadiremos 2 temporadas nuevas!
        gestion.add(new Temporada(4, "Cuatro", "2025-04-01", "2025-04-30"));
        gestion.add(new Temporada(5, "Cinco",  "2025-05-01", "2025-05-31"));

        //llamamos al getAll() para ver que la lista devuelta tiene un tamaño de 2!
        List<Temporada> listaDevuelta = gestion.getAll();
        assertEquals(2, listaDevuelta.size());

        // Modificar la lista devuelta, no debe afectar al gestor!
        //si no tendremos problemas...
        listaDevuelta.clear();
        assertEquals("La lista interna debe seguir teniendo 2", 2, gestion.size());
    }

    //El onjetivo de este método es segurarse de que cuando no haya ninguna coincidencia, se devuelva una lista vacía.
    //Tenemos que prevenir que el método lance excepciones o devuelva "null".
    @Test
    public void testFindByNombreSinCoincidencias() {
        //Empezamos la prueba añadiendo una temporada nueva:
        gestion.add(new Temporada(6, "Alta", "2025-01-01", "2025-01-10"));
        //Al llamar a findByNombre(), debe devolver una lista sin elementos:
        ArrayList<Temporada> resultado = gestion.findByNombre("noexiste");
        assertTrue("Debe devolver lista vacía si no hay coincidencia", resultado.isEmpty());
    }

    //Este último método comprueba la búsqueda de cadenas pero ignorando mayusculas y minusculas.
    //Esto ayuda a verificar que siempre se compare en minusuclas o mayusculas:
    @Test
    public void testFindByNombreVariantes() {
        //Primero añadimos 2 temporadas random:
        gestion.add(new Temporada(7, "Montaña", "2025-02-01", "2025-02-10"));
        gestion.add(new Temporada(8, "Montaña baja", "2025-03-01", "2025-03-10"));
        //Al llamar a FindBy.... se espera que encuentre las instancias que hemos hecho (2)!
        ArrayList<Temporada> res = gestion.findByNombre("MoNtaÑa");
        assertEquals("Tiene que encontrar las 2 coincidencias ingnore case", 2, res.size());
    }
}
