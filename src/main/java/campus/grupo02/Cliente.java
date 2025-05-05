package campus.grupo02;

public class Cliente {
    private int id;
    private String nombre;
    private String identificador;
    private String fecha_nacimiento;

    public Cliente() {

    }

    public Cliente(int id, String nombre, String identificador, String fecha_nacimiento) {
        // Validación de ID aunque las id's son autoincrementales
        if (id != -1 && id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0 o -1 para clientes nuevos");
        }
        
        // Validación de nombre que no puede ser vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        // Validación DNI (DNI/NIE)
        if (identificador != null && !identificador.isEmpty()) {
            // Patrón para DNI: 8 dígitos seguidos de una letra
            // Patrón para NIE: Letra (X, Y, Z) seguida de 7 dígitos y una letra
            String patronDNI = "^[0-9]{8}[A-Za-z]$";
            String patronNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
            
            if (!identificador.matches(patronDNI) && !identificador.matches(patronNIE)) {
                throw new IllegalArgumentException("El formato del identificador (DNI/NIE) no es válido");
            }
        }
        
        // Validación de fecha de nacimiento que no puede ser vacía
        if (fecha_nacimiento == null || fecha_nacimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
        }
        
        // Si usamos formato yyyy-MM-dd, validar que sea una fecha válida para MySQL, no se valida el rango de fechas, se valida el formato
        try {
            java.sql.Date.valueOf(fecha_nacimiento);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El formato de la fecha de nacimiento debe ser YYYY-MM-DD");
        }
        
        this.id = id;
        this.nombre = nombre;
        this.identificador = identificador;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        if (identificador != null && !identificador.isEmpty()) {
            String patronDNI = "^[0-9]{8}[A-Za-z]$";
            String patronNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
            
            if (!identificador.matches(patronDNI) && !identificador.matches(patronNIE)) {
                throw new IllegalArgumentException("El formato del identificador (DNI/NIE) no es válido");
            }
        }
        this.identificador = identificador;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        if (fecha_nacimiento == null || fecha_nacimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
        }
        
        try {
            java.sql.Date.valueOf(fecha_nacimiento);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El formato de la fecha de nacimiento debe ser YYYY-MM-DD");
        }
        
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clientes{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", identificador=").append(identificador);
        sb.append(", fecha_nacimiento=").append(fecha_nacimiento);
        sb.append('}');
        return sb.toString();
    }

    public boolean persist() {
        return EntityManager.Persist(this);
    }

    public boolean merge() {
        return EntityManager.Merge(this);
    }

    public boolean remove() {
        return EntityManager.Remove(this);
    }

    public Cliente[] find() {
        return EntityManager.Find(this);
    }

}
