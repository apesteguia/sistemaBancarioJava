package Modelo;

import java.sql.Date;

public class Transaccion {
    private String remitente;
    private Date fecha;
    private String tipo;
    private long cantidad;
    private String dni;
    private Conexion con = new Conexion();
    private Usuario usr;
    
    public Transaccion(String remitente, Date fecha, String tipo, long cantidad, String dni, Usuario usr) {
        this.remitente = remitente;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.dni = dni;
        this.usr = usr;
    }
    
    public void nuevaTransaccion() {
        con.conectar();
        con.movimientoCajero(this.remitente, this.fecha, this.tipo, this.cantidad, this.dni, this.usr);
        con.desconectar();
    }
}
