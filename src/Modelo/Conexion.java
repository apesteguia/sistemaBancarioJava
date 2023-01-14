package Modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Conexion {

    String bd = "banco";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String pass = "mikel000";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    Statement st;
    ResultSet rs;

    public Conexion() {

    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, pass);
            System.out.println("conectado");
            //JOptionPane.showMessageDialog(null, "Conectado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexion");
        }
        return cx;
    }

    public void desconectar() {
        try {
            cx.close();
        } catch (Exception e) {
            System.out.println("NO se pudo desconectar");
        }
    }

    public Boolean iniciarSesion(String dni, String contr) {
        try {
            st = cx.createStatement();
            rs = st.executeQuery("select * from usuario");
            while (rs.next()) {
                String s = rs.getString(5);
                String c = rs.getString(8);
                String n = rs.getString(1);

                if (s.equals(dni) && c.equals(contr) && s != "" && c != "") {
                    //JOptionPane.showMessageDialog(null, "Bienvenid@ " + n);
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, "No encontrado");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Usuario inexistente");
            return false;
        }
    }

    public Boolean checkDNI(String dni) {
        try {
            st = cx.createStatement();
            rs = st.executeQuery("select * from usuario");
            while (rs.next()) {
                String s = rs.getString(5);

                if (s.equals(dni)) {
                    return true;
                }
            }
            //JOptionPane.showMessageDialog(null, "No encontrado");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Usuario inexistente");
            return false;
        }
    }

    public Usuario usuarioCompleto(String dni) {
        Usuario u;
        try {
            st = cx.createStatement();
            rs = st.executeQuery("select * from usuario");
            while (rs.next()) {
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                String d9 = rs.getString(9);

                if (d5.equals(dni)) {
                    u = new Usuario(d1, d2, d3, d4, d5, d6, d7, d8, d9);
                    return u;
                }
            }
            //JOptionPane.showMessageDialog(null, "No encontrado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Usuario inexistente");

        }
        return u = new Usuario("fdasdf", "asdf");
    }

    public Boolean crearCuenta(String nombre, String ap1, String ap2, String tel, String dni, String direccion, String correo, String contr, String dinero) {
        String query = "insert into usuario (nombre, ap1, ap2, telefono, dni, direccion, correo, contr, dinero)" + " values (?,?,?,?,?,?,?,?,?)";
        int t = Integer.parseInt(tel);
        try {
            System.out.println(nombre + ap1 + ap2 + tel + dni + direccion + correo + contr + dinero);
            PreparedStatement p = cx.prepareStatement(query);
            p.setString(1, nombre);
            p.setString(2, ap1);
            p.setString(3, ap2);
            p.setInt(4, t);
            p.setString(5, dni);
            p.setString(6, direccion);
            p.setString(7, correo);
            p.setString(8, contr);
            p.setString(9, dinero);

            p.execute();
            JOptionPane.showMessageDialog(null, "Contacto creado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de insercion " + e.getMessage());
            return false;
        }
        return true;
    }

    public void getTransacciones(DefaultTableModel modelo, Usuario usr) {
        try {
            st = cx.createStatement();
            rs = st.executeQuery("select * from transacciones join usuario on transacciones.cod = usuario.dni "
                    + "where usuario.nombre='" + usr.returnNombre() + "'");
            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            modelo.addColumn("Remitente");
            modelo.addColumn("Fecha");
            modelo.addColumn("Tipo");
            modelo.addColumn("Cantidad");
            while (rs.next()) {
                Object[] o = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    o[i] = rs.getObject(i + 1);
                }
                modelo.addRow(o);

            }
            //JOptionPane.showMessageDialog(null, "No encontrado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Usuario inexistente");

        }
    }

    public void movimientoCajero(String remitente, Date fecha, String tipo, long cantidad, String dni, Usuario usr) {
        try {
            System.out.println("bien");
            String query = "insert into transacciones (remitente, fecha, tipo, cantidad, cod)" + " values (?,?,?,?,?)";
            PreparedStatement p = cx.prepareStatement(query);
            p.setString(1, remitente);
            p.setDate(2, fecha);
            p.setString(3, tipo);
            p.setLong(4, cantidad);
            p.setString(5, dni);
            p.execute();

            Integer dinUsr = Integer.parseInt(usr.returnDinero());
            if (tipo.equals("ingreso")) {
                dinUsr = dinUsr + (int) cantidad;
            } else {
                dinUsr = dinUsr - (int) cantidad;
            }

            query = "update usuario set dinero='" + dinUsr + "' where dni='" + dni + "'";
            p = cx.prepareStatement(query);
            p.execute();
            JOptionPane.showMessageDialog(null, "Transaccion realizada con exito");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en transaccion " + e.getMessage());
        }
    }

}
