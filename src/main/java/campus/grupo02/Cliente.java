package campus.grupo02;

/**
 * Representa a un cliente del hotel.
 * Contiene información personal y de identificación del cliente.
 */
public class Cliente {
    /**
     * Identificador único del cliente.
     */
    private int id;
    /**
     * Nombre del cliente.
     */
    private String nombre;
    /**
     * Documento de identificación del cliente (DNI/NIE).
     */
    private String identificador;
    /**
     * Fecha de nacimiento del cliente en formato YYYY-MM-DD.
     */
    private String fechaNacimiento;

    /**
     * Constructor por defecto de la clase Cliente.
     * Necesario para ciertas operaciones o frameworks.
     */
    public Cliente() {
        // Constructor vacío
    }

    /**
     * Constructor privado para ser utilizado exclusivamente por el método de fábrica estático.
     * Asigna los valores a los campos después de que hayan sido validados por el método de fábrica.
     * @param id El identificador del cliente.
     * @param nombre El nombre del cliente.
     * @param identificador El DNI/NIE del cliente.
     * @param fechaNacimiento La fecha de nacimiento del cliente.
     */
    private Cliente(final int id, final String nombre, final String identificador, final String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.identificador = identificador;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método de fábrica estático para crear instancias de Cliente.
     * Realiza todas las validaciones necesarias antes de crear el objeto.
     * @param id El identificador del cliente. Debe ser > 0, o -1 para clientes nuevos gestionados por autoincremento.
     * @param nombre El nombre del cliente. No puede ser nulo ni vacío.
     * @param identificador El DNI/NIE del cliente. Debe tener un formato válido si se proporciona.
     * @param fechaNacimiento La fecha de nacimiento del cliente (formato YYYY-MM-DD). No puede ser nula ni vacía y debe tener un formato de fecha válido.
     * @return Una nueva instancia de {@code Cliente} si todos los datos son válidos.
     * @throws IllegalArgumentException Si alguno de los parámetros no cumple con las validaciones especificadas.
     */
    public static Cliente crearCliente(final int id, final String nombre, final String identificador, final String fechaNacimiento) {
        if (id != -1 && id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0 o -1 para clientes nuevos.");
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        
        if (identificador != null && !identificador.trim().isEmpty()) {
            String patronDNI = "^[0-9]{8}[A-Za-z]$";
            String patronNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
            if (!identificador.matches(patronDNI) && !identificador.matches(patronNIE)) {
                throw new IllegalArgumentException("El formato del identificador (DNI/NIE) no es válido.");
            }
        }
        
        if (fechaNacimiento == null || fechaNacimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
        }
        try {
            java.sql.Date.valueOf(fechaNacimiento);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("El formato de la fecha de nacimiento debe ser YYYY-MM-DD.", e);
        }
        
        return new Cliente(id, nombre, identificador, fechaNacimiento);
    }

    /**
     * Obtiene el identificador del cliente.
     * @return El ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del cliente.
     * @param id El nuevo ID del cliente. Debe ser > 0 o -1.
     */
    public void setId(final int id) {
        if (id != -1 && id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0 o -1 para clientes nuevos.");
        }
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre El nuevo nombre del cliente. No puede ser nulo ni vacío.
     */
    public void setNombre(final String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el documento de identificación (DNI/NIE) del cliente.
     * @return El identificador del cliente.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el documento de identificación (DNI/NIE) del cliente.
     * @param identificador El nuevo DNI/NIE del cliente. Debe tener un formato válido si se proporciona.
     */
    public void setIdentificador(final String identificador) {
        if (identificador != null && !identificador.trim().isEmpty()) {
            String patronDNI = "^[0-9]{8}[A-Za-z]$";
            String patronNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
            if (!identificador.matches(patronDNI) && !identificador.matches(patronNIE)) {
                throw new IllegalArgumentException("El formato del identificador (DNI/NIE) no es válido.");
            }
        }
        this.identificador = identificador;
    }

    /**
     * Obtiene la fecha de nacimiento del cliente.
     * @return La fecha de nacimiento en formato YYYY-MM-DD.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del cliente.
     * @param fechaNacimiento La nueva fecha de nacimiento (YYYY-MM-DD). No puede ser nula/vacía y debe ser válida.
     */
    public void setFechaNacimiento(final String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
        }
        try {
            java.sql.Date.valueOf(fechaNacimiento);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("El formato de la fecha de nacimiento debe ser YYYY-MM-DD.", e);
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", identificador='" + identificador + '\'' +
               ", fechaNacimiento='" + fechaNacimiento + '\'' +
               '}';
    }

    /**
     * Persiste el cliente actual en la base de datos.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean persist() {
        return EntityManager.Persist(this);
    }

    /**
     * Actualiza el cliente actual en la base de datos.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean merge() {
        return EntityManager.Merge(this);
    }

    /**
     * Elimina el cliente actual de la base de datos.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean remove() {
        return EntityManager.Remove(this);
    }

    /**
     * Busca clientes en la base de datos basados en los atributos del cliente actual.
     * @return Un array de objetos {@code Cliente} que coinciden con los criterios de búsqueda.
     */
    public Cliente[] find() {
        // Considerar cómo EntityManager.Find maneja la creación de instancias de Cliente.
        // Si crea nuevas instancias, debería usar Cliente.crearCliente si los datos vienen de una fuente no confiable
        // o si necesita validación. Si los datos ya son válidos (ej. de la BBDD), podría usar el constructor privado
        // si EntityManager está en el mismo paquete o es una clase interna, o necesitar un método de ensamblaje.
        return EntityManager.Find(this);
    }
}