package campus.grupo02;

public class Hotel {
    private int id;
    private String nombre;
    private int num_plantas;
    private String localizacion;

    public Hotel() {

    }

    public Hotel(int id, String nombre, int num_plantas, String localizacion) {
        this.id = id;

        /*if (nombre == null || nombre.equals("") ) {
             System.out.println("tiene que haber un nombre en el hotel ");        }
             else{
                this.nombre = nombre;

             }*/

        if(nombre == null || nombre.equals("")){
            System.out.println("El nombre no puede ser vacío");
        }
        this.nombre = nombre;

        /*if (num_plantas<0) {
            System.out.println("el numero de plantas no puede ser menor  que 0");
        }
        else {
            this.num_plantas = num_plantas;

        }

         */
        if (num_plantas < 0){
            System.out.println("El número de plantas no puede ser menor que 0");
        }
        this.localizacion = localizacion;
        this.num_plantas = num_plantas;

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

    public int getNum_plantas() {
        return num_plantas;
    }

    public void setNum_plantas(int num_plantas) {
        this.num_plantas = num_plantas;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hotel{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", num_plantas=").append(num_plantas);
        sb.append(", localizacion=").append(localizacion);
        sb.append('}');
        return sb.toString();
    }


 /**
     * Se utiliza para añadir los datos de la instancia a la tabla
     * 
     * @return True si la operación ha ido bien. False, en caso contrario
     */
    public boolean persist() {
        return EntityManager.Persist(this);

    }

    /**
     * Se utiliza para actualizar los datos de la instancia a la tabla.
     * Para simplificar, actualizaremos todo excepto lo que corresponde con la
     * PK* @return True si la operación ha ido bien. False, en caso contrario
     */
    public boolean merge() {
        return EntityManager.Merge(this);

    }

    /**
     * Se utiliza para eliminar los datos de la instancia a la tabla
     * Para simplificar, eliminaremos a partir de la PK
     * 
     * @return True si la operación ha ido bien. False, en caso contrario
     */
    public boolean remove() {
        return EntityManager.Remove(this);

    }

    /**
     * Busca los hoteles que cumplen las condiciones de esta instancia.
     * Solo se tiene en cuenta {@code job_title} (con <i>LIKE</i>) y que el salario
     * sea {@literal <=} maxSalary y {@literal >=} minSalary.  
     * Si {@code job_title} es {@code null} o min/maxSalary valen {@code -1},
     * esos filtros no se aplican.
     *
     * @return un array con los hoteles encontrados, o {@code null} si no hay ninguno
     */
    public Hotel[] find() {
        return EntityManager.Find(this);

    }





}


