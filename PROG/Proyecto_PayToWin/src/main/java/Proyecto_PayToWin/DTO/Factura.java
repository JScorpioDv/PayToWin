/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class Factura {
    
    //ATRIBUTOS
    private int codigo;
    private LocalDateTime fecha;
    private int iva;
    private Pedido pedido;
    private Direccion direcFactura;
    
    //CONSTRUCTORES
    public Factura(int codigo, LocalDateTime fecha, int iva, Pedido pedido, Direccion direcFactura) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.iva = iva;
        this.pedido = pedido;
        this.direcFactura = direcFactura;
    }
    
    //GETTERS Y SETTERS

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Direccion getDirecFactura() {
        return direcFactura;
    }

    public void setDirecFactura(Direccion direcFactura) {
        this.direcFactura = direcFactura;
    }
    
    public void imprimir(){
        System.out.println("Factura (Codigo= " + codigo + ", Fecha= " + fecha + ", IVA= " + iva + ", Pedido= " + pedido + ", DirecFactura= " + direcFactura + ')');
    }

    @Override
    public String toString() {
        return "Factura (Codigo= " + codigo + ", Fecha= " + fecha + ", IVA= " + iva + ", Pedido= " + pedido + ", DirecFactura= " + direcFactura + ')';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factura other = (Factura) obj;
        return this.codigo == other.codigo;
    }
    
    
    
}
