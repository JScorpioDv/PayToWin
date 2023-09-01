/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author juanp
 */
public class Pedido {
    
    //ATRIBUTOS
    private int codigo;
    private LocalDateTime fecha;
    private TiposPago metpago;
    private String facturado;
    private Direccion direccion;
    private Usuario cliente;
    private HashMap<Producto, Map.Entry<Integer, Double>> lineas;

    //CONSTRUCTORES

    public Pedido(int codigo, LocalDateTime fecha, TiposPago metpago, String facturado, Direccion direccion, Usuario cliente, HashMap<Producto, Map.Entry<Integer, Double>> lineas) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.metpago = metpago;
        this.facturado = facturado;
        this.direccion = direccion;
        this.cliente = cliente;
        this.lineas = lineas;
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

    public TiposPago getMetpago() {
        return metpago;
    }

    public void setMetpago(TiposPago metpago) {
        this.metpago = metpago;
    }

    public String getFacturado() {
        return facturado;
    }

    public void setFacturado(String facturado) {
        this.facturado = facturado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public HashMap<Producto, Map.Entry<Integer, Double>> getLineas() {
        return lineas;
    }

    public void setLineas(HashMap<Producto, Map.Entry<Integer, Double>> lineas) {
        this.lineas = lineas;
    }
    
    public Double getPrecioTotal(){
        double precioTotal = 0;
        
        for (Map.Entry<Producto, Map.Entry<Integer, Double>> linea: lineas.entrySet()) {
            precioTotal += linea.getValue().getKey() * linea.getValue().getValue();
        }
        
        return precioTotal;
    }
    
    
    
    //METODES
    public void imprimir(){
        System.out.println("Pedido (Codigo=" + codigo + ", Fecha=" + fecha + ", Metpago=" + metpago + ", Facturado=" + facturado + ", Direccion=" + direccion + ", Cliente=" + cliente + ')');
    }

    @Override
    public String toString() {
        return "Pedido (Codigo=" + codigo + ", Fecha=" + fecha + ", Metpago=" + metpago + ", Facturado=" + facturado + ", Direccion=" + direccion + ", Cliente=" + cliente + ')';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.codigo;
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
        final Pedido other = (Pedido) obj;
        return this.codigo == other.codigo;
    }

    public boolean revisarStock() {
        for(Map.Entry<Producto, Map.Entry<Integer, Double>> linea: lineas.entrySet()){
            if(linea.getKey().getStock() - linea.getValue().getKey() < linea.getKey().getStockMinimo()) return false;
        }
        return true;
    }
    
    public boolean perteneceAUsuario(Usuario u){
        return this.cliente.equals(u);
    }
    
    
    
}
