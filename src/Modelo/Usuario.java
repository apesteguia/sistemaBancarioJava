package Modelo;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class Usuario {

    private String nombre;
    private String ap1;
    private String ap2;
    private String tel;
    private String dni;
    private String direccion;
    private String correo;
    private String contr;
    private String dinero;
    private Conexion con = new Conexion();

    public Usuario(String dni, String contr) {
        this.dni = dni;
        this.contr = contr;
    }

    public Usuario(String nombre, String ap1, String ap2, String tel, String dni, String direccion, String correo, String contr, String dinero) {
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.tel = tel;
        this.dni = dni;
        this.direccion = direccion;
        this.correo = correo;
        this.contr = contr;
        this.dinero = dinero;
    }

    public Boolean iniciarSesion(String dni, String contr) {
        con.conectar();
        Boolean b = con.iniciarSesion(dni, contr);
        if (b == false) {
            return false;
        }
        con.desconectar();
        return true;
    }
    
    public Boolean checkDNI(String dni) {
        con.conectar();
        Boolean b = con.checkDNI(dni);
        if (b == false) {
            return false;
        }
        con.desconectar();
        return true;
    }

    public String returnDNI() {
        return this.dni;
    }
    
    public String returnNombre() {
        return this.nombre;
    }
    
    public String returnDinero() {
        return this.dinero;
    }

    public Usuario todoUsuario() {
        con.conectar();
        Usuario e = con.usuarioCompleto(this.dni);
        con.desconectar();
        return e;
    }

    public void crearCuenta() {
        con.conectar();
        con.crearCuenta(this.nombre, this.ap1, this.ap2, this.tel, this.dni, this.direccion, this.correo, this.contr, this.dinero);
        con.desconectar();
    }
}
