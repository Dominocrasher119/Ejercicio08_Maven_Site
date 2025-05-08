package campus.grupo02;

public class Temporada {
    private int id;
    private String nombre;
    private String fecha_inicio;
    private String fecha_fin;

    //constructor:
    public Temporada() {

    }
    //constructor con atributos:
    public Temporada(int id, String nombre, String fecha_inicio, String fecha_fin) {

        //****DENTRO DEL CONSTRUCTOR EMPEZAMOS A COMPROBAR COSITAS...****

        //PRIMERO VALIDAMOS EL ID:
        if(id != -1 && id <= 0){
            throw new IllegalArgumentException("El ID debe ser mayor que 0 o -1 para temporadas nuevas");
        }

        //AHORA VALIDAMOS EL NOMBRE QUE SE DA A LA TEMPORADA:
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la temporada no puede estar vacío");
        }

        //VALIDAMOS LAS FECHAS:
        if(fecha_inicio == null || fecha_inicio.trim().isEmpty()){
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacía");
        }
        //validamos la fecha de fin de temporada:
        if(fecha_fin == null || fecha_fin.trim().isEmpty()){
            throw new IllegalArgumentException("La fecha de fin no puede estar vacía");
        }

        //QUEREMOS QUE SE ACEPTEN AMBOS FORMATOS PARA LAS FECHAS (DD-MM-YYYY) Y (YYYY-MMM-DD):
        String inicioSQL = convertirFormatoFechaIfNeeded(fecha_inicio);
        String finSQL = convertirFormatoFechaIfNeeded(fecha_fin);

        java.sql.Date ini = java.sql.Date.valueOf(inicioSQL);
        java.sql.Date fin = java.sql.Date.valueOf(finSQL);

        if(fin.before(ini)){
            throw new IllegalArgumentException("La fecha de fin tiene que ser mayor a la fecha de inicio!");
        }

        //NOS ASEGURAMOS DE QUE LAS ASIGNACIONES SE HAGAN DE FORMA CORRECTA!
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = inicioSQL;
        this.fecha_fin = finSQL;
    }

    //USAMOS ESTE MÉTODO PARA QUE (SI ES NECESARIO) SE CAMBIE EL FORMATO DE FECHA PARA QUE ENTRE BIEN EN LA BBDD!!
    private String convertirFormatoFechaIfNeeded(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }
        
        String[] partes = fecha.split("-");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato de fecha incorrecto. Debe ser DD-MM-YYYY o YYYY-MM-DD");
        }
        
        // TENEMOS QUE ASEGURARNOS DE QUE EL PRIMERO SEGMENTO TIENE 4 DÍGITOS (ASUMIMOS QUE YA ESTÁ EN FORMATO YYY-MM-DD)
        if (partes[0].length() == 4) {
            return fecha;
        }
        
        // Si el primer segmento tiene 2 caracteres, asumimos formato DD-MM-YYYY y lo convertimos
        if (partes[0].length() == 2 && partes[2].length() == 4) {
            return partes[2] + "-" + partes[1] + "-" + partes[0];
        }
        
        throw new IllegalArgumentException("Formato de fecha no reconocido");
    }


    //AQUÍ ESTABLECEMOS LOS GETTERS Y LOS SETTERS CON LAS VALIDACIONES CORRESPONDIENTES:
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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la temporada no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre de la temporada no puede superar los 50 caracteres");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre de la temporada debe tener al menos 3 caracteres");
        }
        // Opcional: validar caracteres permitidos
        if (!nombre.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚüÜñÑ ]+")) {
            throw new IllegalArgumentException("El nombre contiene caracteres no válidos");
        }
        this.nombre = nombre;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        validarFechaInicio(fecha_inicio);
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }
    public void setFecha_fin(String fecha_fin) {
        validarFechaFin(fecha_fin);
        this.fecha_fin = fecha_fin;
    }

    //AHORA VAMOS A VALIDAR LOS SETTERS DE LAS FECHAS:

    private void validarFechaInicio(String fecha_inicio) {
        if (fecha_inicio == null || fecha_inicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacía!");
        }

        try {
            java.sql.Date fechaIni = java.sql.Date.valueOf(fecha_inicio);

            // Si hay una fecha de fin establecida, verificar que es posterior
            if (this.fecha_fin != null && !this.fecha_fin.isEmpty()) {
                java.sql.Date fechaFin = java.sql.Date.valueOf(this.fecha_fin);
                if (fechaFin.before(fechaIni)) {
                    throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
                }
            }

            // Validar que la fecha no está en un pasado muy lejano (anterior a 2000)
            if (fechaIni.before(java.sql.Date.valueOf("2000-01-01"))) {
                throw new IllegalArgumentException("La fecha de inicio no puede ser anterior al año 2000");
            }

            // Validar que la fecha no está en un futuro muy lejano (más de 10 años)
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.YEAR, 10);
            if (fechaIni.after(new java.sql.Date(cal.getTimeInMillis()))) {
                throw new IllegalArgumentException("La fecha de inicio no puede ser superior a 10 años en el futuro");
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La fecha de inicio debe tener el formato YYYY-MM-DD");
        }
    }


    public void validarFechaFin(String fecha_fin) {
        if (fecha_fin == null || fecha_fin.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de fin no puede estar vacía");
        }
        
        // Validar el formato de la fecha
        try {
            java.sql.Date fechaFin = java.sql.Date.valueOf(fecha_fin);
            
            // Si hay una fecha de inicio establecida, verificar que es anterior
            if (this.fecha_inicio != null && !this.fecha_inicio.isEmpty()) {
                java.sql.Date fechaIni = java.sql.Date.valueOf(this.fecha_inicio);
                if (fechaFin.before(fechaIni)) {
                    throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
                }
                
                // Validar duración mínima de la temporada (al menos 1 día)
                long diff = fechaFin.getTime() - fechaIni.getTime();
                if (diff < 24*60*60*1000) { // menos de 1 día en milisegundos
                    throw new IllegalArgumentException("La temporada debe durar al menos 1 día");
                }
                
                // Validar duración máxima de la temporada (no más de 365 días)
                if (diff > 365L*24*60*60*1000) {
                    throw new IllegalArgumentException("La temporada no puede durar más de 365 días");
                }
            }
            
            // Otras validaciones como en setFecha_inicio()
            if (fechaFin.before(java.sql.Date.valueOf("2000-01-01"))) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior al año 2000");
            }

            //Comprobar que la fecha de fin no sea de 10 en el futuro:
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.YEAR, 10);
            if (fechaFin.after(new java.sql.Date(cal.getTimeInMillis()))) {
                throw new IllegalArgumentException("La fecha de fin no puede ser superior a 10 años en el futuro");
            }
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La fecha de fin debe tener el formato YYYY-MM-DD");
        }
    }

    @Override
    public String toString() {
        return "Temporada [id=" + id + ", nombre=" + nombre + ", fecha_inicio=" + fecha_inicio + ", fecha_fin="
                + fecha_fin + "]";
    }

    /**
     * Se utiliza para añadir los datos de la instancia a la tabla
     *
     * @return True si la operación ha ido bien. False, en caso contrario
     */
    //aqui iria el insert a la bbdd:
    public boolean persist() {
        return EntityManager.Persist(this);
        //return false;
    }

    /**
     * Se utiliza para actualizar los datos de la instancia a la tabla.
     * Para simplificar, actualizaremos todo excepto lo que corresponde con la
     * PK* @return True si la operación ha ido bien. False, en caso contrario
     */
    //aqui va update hacia la bbdd:
    public boolean merge() {
        return EntityManager.Merge(this);
    }

    /**
     * Se utiliza para eliminar los datos de la instancia a la tabla
     * Para simplificar, eliminaremos a partir de la PK
     *
     * @return True si la operación ha ido bien. False, en caso contrario
     */
    //aqui va el delete hacia la bbdd:
    public boolean remove() {
        return EntityManager.Remove(this);
    }

    /**
     * Busca las temporadas que cumplen los criterios indicados en esta instancia.
     * <p>
     * Solo se tendrá en cuenta el campo {@code nombre} (con <i>LIKE</i>) y que la
     * duración esté comprendida entre {@literal <=} {@code maxDays} y
     * {@literal >=} {@code minDays}.  
     * Si {@code nombre} es {@code null} o {@code minDays/maxDays} valen {@code -1},
     * esos filtros se ignorarán.
     *
     * @return un array con las temporadas encontradas, o {@code null} si no se halló
     *         ninguna coincidencia
     */
    //aqui va el select hacia la bbdd:
    public Temporada[] find() {
        return EntityManager.Find(this);
    }
}
