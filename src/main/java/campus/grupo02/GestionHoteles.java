package campus.grupo02;

import java.util.ArrayList;

public class GestionHoteles {
    private ArrayList<Hotel> hoteles;

    public GestionHoteles() {
        hoteles = new ArrayList<>();
    }

    // Devuelve el hotel que hay en la posición indicada, o null si la posición está fuera de rango
    public Hotel get(int pos) {
        if (pos < 0 || pos >= hoteles.size()) {
            return null;
        }
        return hoteles.get(pos);
    }

    // Devuelve el hotel con el id indicado, o null si no existe
    public Hotel getById(int id) {
        for (Hotel hotel : hoteles) {
            if (hotel.getId() == id) {
                return hotel;
            }
        }
        return null;
    }

    // Añade un hotel a la lista de hoteles, si el hotel ya existe no lo añade y devuelve false
    public boolean add(Hotel hotel) {
        if (getById(hotel.getId()) != null) {
            return false; // El hotel ya existe
        }
        hoteles.add(hotel);
        return true; // Hotel añadido con éxito
    }

    // Busca si el hotel tiene el mismo id que el que pasamos como parámetro, si lo encuentra lo sustituye por el nuevo hotel, si no, devuelve false
    public boolean replace(Hotel hotel) {
        for (int i = 0; i < hoteles.size(); i++) {
            if (hoteles.get(i).getId() == hotel.getId()) {
                hoteles.set(i, hotel);
                return true;
            }
        }
        return false;
    }

    // Elimina un hotel de la lista
    public boolean remove(Hotel hotel) {
        return hoteles.remove(hotel);
    }

    // Elimina el hotel con el id indicado en la lista
    public boolean remove(int id) {
        Hotel hotel = getById(id);
        if (hotel != null) {
            hoteles.remove(hotel);
            return true;
        }
        return false;
    }

    // Devuelve todos los hoteles
    public ArrayList<Hotel> getAll() {
        return new ArrayList<>(hoteles); // Devuelve una copia para evitar modificaciones directas
    }

    // Devuelve el número total de hoteles
    public int size() {
        return hoteles.size();
    }

    // Indica si la lista de hoteles está vacía
    public boolean isEmpty() {
        return hoteles.isEmpty();
    }

    // Busca hoteles por nombre (parcial o completo)
    public ArrayList<Hotel> findByNombre(String nombre) {
        ArrayList<Hotel> resultado = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }

    // Elimina todos los hoteles de la lista
    public void clear() {
        hoteles.clear();
    }
}