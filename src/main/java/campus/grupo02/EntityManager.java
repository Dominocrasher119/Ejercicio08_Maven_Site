package campus.grupo02;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EntityManager {

    private static Connection conn;

    //Statements para los inserts contra la BBDD:
    private static PreparedStatement psInsertClientes;
    private static PreparedStatement psSelectIdClientes;
    private static PreparedStatement psUpdateTodoClientes;
    private static PreparedStatement psDeleteClientes;

    private static PreparedStatement psInsertTemporada;
    private static PreparedStatement psUpdateTodoTemporada;
    private static PreparedStatement psDeleteTemporada;

    private static PreparedStatement psInsertHotel;
    private static PreparedStatement psUpdatetodoHotel  ;
    private static PreparedStatement psDeleteHotel;
    private static PreparedStatement psSelectIDHotel;

    private static Statement stmt;

    public boolean init() {
        try {
            Properties p = loadPropertiesFile();
            if (p == null) {
                return false;
            }

            String strConn = (String) p.get("db.string_connection");
            System.out.println(strConn);
            conn = DriverManager.getConnection(strConn);

            // Preparar los PreparedStatements para Clientes
            
            //Insert Cliente
            String strInsertClientes = "INSERT INTO Clientes (nombre, identificador, fecha_nacimiento) VALUES (?,?,?)";
            psInsertClientes = conn.prepareStatement(strInsertClientes);

            // Seleccionar ID cliente
            String strSelectIdClientes = "Select id FROM Clientes WHERE nombre=?";
            psSelectIdClientes = conn.prepareStatement(strSelectIdClientes);

            // Actualizar todos los campos de Clientes, basándonos en id
            String strUpdateClientes = "UPDATE Clientes SET nombre=?, identificador=?, fecha_nacimiento=? WHERE id=?";
            psUpdateTodoClientes = conn.prepareStatement(strUpdateClientes);

            // Eliminar cliente por id
            String strDeleteClientes = "DELETE FROM Clientes WHERE id=?";
            psDeleteClientes = conn.prepareStatement(strDeleteClientes);


            //ORDENES CONTRA BBDD, TABLA TEMPORADA:
            // Hacer Insert de Temporada nueva:
            String strInsertTemporada = "INSERT INTO Temporada (nombre, fecha_inicio, fecha_fin) VALUES (?,?,?)";
            psInsertTemporada = conn.prepareStatement(strInsertTemporada);
            // Actualizar todos los campos de Temporada, seleccionándola por el ID:
            String strUpdateTodoTemporada = "UPDATE Temporada SET nombre=?, fecha_inicio=?, fecha_fin=? WHERE id=?";
            psUpdateTodoTemporada = conn.prepareStatement(strUpdateTodoTemporada);
            // Eliminar Temporada de la BBD:
            String strDeleteTemporada = "DELETE FROM Temporada WHERE id=?";
            psDeleteTemporada = conn.prepareStatement(strDeleteTemporada);

            
            


            // HOTELES
            //Insert hotel
            String strInsertHotel = "INSERT INTO Hoteles (nombre, num_plantas, direccion) VALUES (?, ?, ?)";
            psInsertHotel = conn.prepareStatement(strInsertHotel);
            // seleccionar ID hotel
            String strSelectIdHotel = "SELECT * FROM Hoteles WHERE id = ?";
            psSelectIDHotel = conn.prepareStatement(strSelectIdHotel);
            // update todo hotel
            String strUpdateTodo = "UPDATE Hoteles SET nombre = ?, num_plantas = ?, direccion = ? WHERE id = ?";
            psUpdatetodoHotel = conn.prepareStatement(strUpdateTodo);
            String strDeleteHotel = "DELETE FROM Hoteles WHERE id = ?";
            psDeleteHotel = conn.prepareStatement(strDeleteHotel);

            stmt = conn.createStatement();
            return true;
        } catch (SQLException e) {
            showError(e);
            unLoad();
            return false;
        }
    }

    public static void showError(SQLException e) {
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

    public void unLoad() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Properties loadPropertiesFile() {
        Properties p = new Properties();
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("config.properties");
            p.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }


    //MÉTODOS GENERALES:
    public static int psSelectidCliente(String nombreCliente) {
        try {
            psSelectIdClientes.setString(1, nombreCliente);
            var rs = psSelectIdClientes.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
            return -1;
        }
    }

    public static void printClientes() {
        try {
            String sql = "SELECT * FROM Clientes";
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Identificador: " + rs.getString("identificador") + ", Fecha de nacimiento: " + rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
    }
    public static int psSelectidHotel(String nombreHotel) {
        try {
            psSelectIDHotel.setString(1, nombreHotel);
            var rs = psSelectIDHotel.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
            return -1;
        }
    }

    public static void printHoteles() {
        try {
            String sql = "SELECT * FROM Hoteles";
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", num_plantas: " + rs.getString("num_plantas") + ", direccion: " + rs.getString("direccion"));
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
    }

  
    public static Cliente[] Find(Cliente clientes) {
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Clientes WHERE 1=1");

            // Construir la consulta dinámicamente según los campos proporcionados
            if (clientes.getNombre() != null) {
                sqlBuilder.append(" AND nombre LIKE ?");
            }
            if (clientes.getIdentificador() != null) {
                sqlBuilder.append(" AND identificador LIKE ?");
            }
            if (clientes.getFecha_nacimiento() != null) {
                sqlBuilder.append(" AND fecha_nacimiento LIKE ?");
            }

            PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString());

            // Asignar los valores a los parámetros
            int paramIndex = 1;
            if (clientes.getNombre() != null) {
                ps.setString(paramIndex++, clientes.getNombre() + "%");
            }
            if (clientes.getIdentificador() != null) {
                ps.setString(paramIndex++, clientes.getIdentificador() + "%");
            }
            if (clientes.getFecha_nacimiento() != null) {
                ps.setString(paramIndex++, clientes.getFecha_nacimiento() + "%");
            }

            ResultSet rs = ps.executeQuery();
            Cliente[] result = new Cliente[20];
            int i = 0;
            while (rs.next()) {
                if (i < result.length) {
                    result[i] = new Cliente(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("identificador"),
                            rs.getString("fecha_nacimiento"));
                    i++;
                } else {
                    break;
                }
            }
            return result;
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        return null;
    }

   
    public static boolean Persist(Cliente clientes) {
        try {
            psInsertClientes.setString(1, clientes.getNombre());
            psInsertClientes.setString(2, clientes.getIdentificador());
            psInsertClientes.setString(3, clientes.getFecha_nacimiento());
            psInsertClientes.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        return false;
    }

  
    public static boolean Merge(Cliente clientes) {
        try {
            psUpdateTodoClientes.setString(1, clientes.getNombre());
            psUpdateTodoClientes.setString(2, clientes.getIdentificador());
            psUpdateTodoClientes.setString(3, clientes.getFecha_nacimiento());
            psUpdateTodoClientes.setInt(4, clientes.getId());
            psUpdateTodoClientes.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        return false;
    }

  
    public static boolean Remove(Cliente clientes) {
        try {
            psDeleteClientes.setInt(1, clientes.getId());
            psDeleteClientes.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        return false;
    }

    //MÉTODOS PARA LA CLASE TEMPORADA!!:
    //con el método "find" podemos hacer un SELECT contra la BBDD del proyecto:
    public static Temporada[] Find(Temporada temporada) {
        List<Temporada> temporadas = new ArrayList<Temporada>();
        try {
            String sql = "SELECT * FROM Temporada";
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String fechaIni = rs.getString("fecha_inicio");
                String fechaFin = rs.getString("fecha_fin");
                temporadas.add(new Temporada(id, nombre, fechaIni, fechaFin));
//                System.out.println("ID: " + id +
//                        ", Nombre: " + nombre +
//                        ", Fecha Inicio: " + fechaIni +
//                        ", Fecha Fin: " + fechaFin);
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        //return (Temporada[])temporadas.toArray();
        return temporadas.toArray(new Temporada[0]);
    }

    //este método Persist equivale al INSERT de una temporada nueva a la bbdd:
    public static boolean Persist(Temporada temporada) {
        try {
            psInsertTemporada.setString(1, temporada.getNombre());
            psInsertTemporada.setString(2, temporada.getFecha_inicio());
            psInsertTemporada.setString(3, temporada.getFecha_fin());
            psInsertTemporada.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }

    }

    //este método Merge equivale al UPDATE de una temporada a la bbdd:
    public static boolean Merge(Temporada temporada) {

        try {
            psUpdateTodoTemporada.setString(1, temporada.getNombre());
            psUpdateTodoTemporada.setString(2, temporada.getFecha_inicio());
            psUpdateTodoTemporada.setString(3, temporada.getFecha_fin());
            psUpdateTodoTemporada.setInt(4, temporada.getId());

            psUpdateTodoTemporada.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }
    }
    //Éste método equivale al DELETE contra la BBD del proyecto:
    public static boolean Remove(Temporada temporada) {

        try {
            psDeleteTemporada.setInt(1, temporada.getId());
            int rows = psDeleteTemporada.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }
    }




     //métodos para la clase Hotel:
     public static Hotel[] Find(Hotel hotel) {
        List<Hotel> hoteles = new ArrayList<Hotel>();
        try {
            String sql = "SELECT * FROM Hoteles";
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int numPlantas = rs.getInt("num_plantas");
                String direccion = rs.getString("direccion");
                hoteles.add(new Hotel(id, nombre, numPlantas, direccion));
            }
        } catch (SQLException e) {
            EntityManager.showError(e);
        }
        return hoteles.toArray(new Hotel[0]);
    }

    public static boolean Persist(Hotel hotel) {
        try {
            psInsertHotel.setString(1, hotel.getNombre());
            psInsertHotel.setInt(2, hotel.getNum_plantas());
            psInsertHotel.setString(3, hotel.getLocalizacion());
            psInsertHotel.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }
    }

    public static boolean Merge(Hotel hotel) {
        try {
            psUpdatetodoHotel.setString(1, hotel.getNombre());
            psUpdatetodoHotel.setInt(2, hotel.getNum_plantas());
            psUpdatetodoHotel.setString(3, hotel.getLocalizacion());
            psUpdatetodoHotel.setInt(4, hotel.getId());
            psUpdatetodoHotel.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }
    }

    public static boolean Remove(Hotel hotel) {
        try {
            psDeleteHotel.setInt(1, hotel.getId());
            psDeleteHotel.executeUpdate();
            return true;
        } catch (SQLException e) {
            EntityManager.showError(e);
            return false;
        }
    }

}
