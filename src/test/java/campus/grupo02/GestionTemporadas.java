package campus.grupo02;

//Con ésta clase Gestora, podemos mantener un listado de Temporadas en memoria: Dar de alta, modificar, consultarlas, etc.

import java.util.ArrayList;


public class GestionTemporadas {
    //En esta lista guardamos las temporadas en memoria:
    private ArrayList<Temporada> temporadas;

    //Ahora necesitamos un constructor que inicialice la lista interna:
    public GestionTemporadas() {
        temporadas = new ArrayList<Temporada>();
    }

    //El codifo que viene a continuacion devolverá la temporada que encuentre en la posicion indicada:
    //el parametro "pos" es la posicion indicada
    //devolverá la temporada de dicha posicion o null si la posicion dada queda fuera del rango:
    public Temporada get(int pos){
        if(pos < 0 || pos >= temporadas.size()){
            return null;
        }
        return temporadas.get(pos);
    }

    //Ahora necesitamos que se devuelva la temporada con el id indicado:
    public Temporada getById(int id){
        for(int i = 0; i < temporadas.size(); i++){
            if(temporadas.get(i).getId() == id){
                return temporadas.get(i);
            }
        }
        return null;
    }

    //Añadir una nueva temporada a la lista:
    public boolean add(Temporada temporada){
        if(getById(temporada.getId()) != null){
            return false;
        }
        temporadas.add(temporada);
        return true;
    }

    //Sustituir la temporada existente con el mismo id:
    public boolean replace(Temporada temporada){
        for(int i = 0; i < temporadas.size(); i++){
            if(temporadas.get(i).getId() == temporada.getId()){
                temporadas.set(i, temporada);
                return true;
            }
        }
        return false;
    }

    //Eliminar la temporada que está indicada:
    public boolean remove(Temporada temporada){
        for(int i = 0; i < temporadas.size(); i++){
            if(temporadas.get(i).getId() == temporada.getId()){
                temporadas.remove(i);
                return true;
            }
        }
        return false;
    }

    //eliminar la temporada indicada pero por su Id!
    public boolean remove(int id){
        for(int i = 0; i < temporadas.size(); i++){
            if(temporadas.get(i).getId() == id){
                temporadas.remove(i);
                return true;
            }
        }
        return false;
    }

    //A continuación preparamos algunos métodos opcionales:

    //Devolver copia de las temporadas:
    public ArrayList<Temporada> getAll(){
        return new ArrayList<>(temporadas);
    }

    //devuelve el número de temporadas almacenadas:
    public int size(){
        return temporadas.size();
    }

    //También necesitamos un método que nos indique si no hay temporadas almacenadas:
    public boolean isEmpty(){
        return temporadas.isEmpty();
    }

    //Método para buscar las temporadas en funcion del texto proporcionado:
    public ArrayList<Temporada> findByNombre(String nombre){
        ArrayList<Temporada> resultado = new ArrayList<Temporada>();
        String filtro = nombre.toLowerCase();
        for(int i = 0; i < temporadas.size(); i++){
            Temporada t = temporadas.get(i);
            if(t.getNombre().toLowerCase().contains(filtro)){
                resultado.add(t);
            }
        }
        return resultado;
    }
}
