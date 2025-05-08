package campus.grupo02;

import java.util.ArrayList;

public class GestionClientes {
    private ArrayList<Cliente> clientes;

    public GestionClientes() {
        clientes = new ArrayList<Cliente>();
    }

    // devuelve el cliente que hay en la posicion indicada, null si la posicion esta fuera de rango
    public Cliente get(int pos) {
        if (pos < 0 || pos >= clientes.size()) {
            return null;
        }
        return clientes.get(pos);
    }

    // Devuelve todos los datos de los clientes que tiene el id indicado, la id del la atraccion que esta buscando, hara un return si los clientes tiene esta id, sino devuelve null
    public Cliente getById(int id) {
            for (int x = 0; x < clientes.size(); x++) {
                if (clientes.get(x).getId() == id) {
                    return clientes.get(x);
                }
            }       
        return null;
    }

    //Añade los clientes a la lista de clientes, si el cliente ya existe no lo añade y devuelve false, si lo añade devuelve true
    public boolean add(Cliente cliente) {
        if (getById(cliente.getId()) != null) {
            return false; // El cliente ya existe
        }
        clientes.add(cliente);
        return true; // Cliente añadido con éxito
    }

    //Busca si el cliente tiene el mismo id que le pasamos como parametro, si lo encuentra, lo sustituye por el nuevo cliente, si no lo encuentra devuelve false
    public boolean replace(Cliente cliente) {
        for (int x = 0; x < clientes.size(); x++) {
            if (clientes.get(x).getId() == cliente.getId()) {
                clientes.set(x, cliente);
                return true;
            }
        }
        return false;
    }

    //Elimina el cliente que esta en la lista, el parametro atraccion a eliminar, retorna true si lo ha encontrado y eliminado, false en caso contrario
    public boolean remove(Cliente cliente) {
        for (int x = 0; x < clientes.size(); x++) {
            if (clientes.get(x).getId() == cliente.getId()) {
                clientes.remove(x);
                return true;
            }
        }
        return false;
    }

    //Elimina la atraccion con el id indicado en la lista, el parametro que le insertamos el la id a eliminar, retorna true si lo ha encontrado y eliminado, false en caso contrario
    public boolean remove(int id) {
        for (int x = 0; x < clientes.size(); x++) {
            if (clientes.get(x).getId() == id) {
                clientes.remove(x);
                return true;
            }
        }
        return false;
    }

    // a partir de aqui son metodos que me ha sugerido el chatgpt, no se si son necesarios o no, pero los he dejado por si acaso
    
        // Devuelve todos los clientes
    public ArrayList<Cliente> getAll() {
        return new ArrayList<>(clientes);  // Devuelve una copia para evitar modificaciones directas
    }
    
    // Devuelve el número de clientes
    public int size() {
        return clientes.size();
    }
    
    // Indica si no hay clientes
    public boolean isEmpty() {
        return clientes.isEmpty();
    }
    
    // Busca clientes por nombre (parcial o completo)
    public ArrayList<Cliente> findByNombre(String nombre) {
        ArrayList<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }
    
    // Busca cliente por identificador (DNI/NIE)
    public Cliente findByIdentificador(String identificador) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdentificador().equals(identificador)) {
                return cliente;
            }
        }
        return null;
    }
    
    // Elimina todos los clientes
    public void clear() {
        clientes.clear();
    }


}
