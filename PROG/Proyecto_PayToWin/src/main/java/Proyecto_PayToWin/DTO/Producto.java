/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class Producto {

    //ATRIBUTOS
    private int codigo; //CLAVE
    private String nombre;
    private double precio;
    private int iva;
    private int stockMinimo;
    private int stock;
    private String foto;
    private Usuario usuarioCrea;
    private LocalDateTime fechaCrea;
    private Usuario usuarioModif;
    private LocalDateTime fechaModif;
    private String descripcion;
    private LinkedHashSet<Categoria> categoria;
    
    //CONSTRUCTORES

    public Producto(int codigo, String nombre, double precio, int iva, int stockMinimo, int stock, String foto, Usuario usuarioCrea, LocalDateTime fechaCrea, Usuario usuarioModif, LocalDateTime fechaModif, String descripcion, LinkedHashSet<Categoria> categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.iva = iva;
        this.stockMinimo = stockMinimo;
        this.stock = stock;
        this.foto = foto;
        this.usuarioCrea = usuarioCrea;
        this.fechaCrea = fechaCrea;
        this.usuarioModif = usuarioModif;
        this.fechaModif = fechaModif;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }
    
    //GETTERS Y SETTERS

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Usuario usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public LocalDateTime getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(LocalDateTime fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Usuario getUsuarioModif() {
        return usuarioModif;
    }

    public void setUsuarioModif(Usuario usuarioModif) {
        this.usuarioModif = usuarioModif;
    }

    public LocalDateTime getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(LocalDateTime fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LinkedHashSet<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(LinkedHashSet<Categoria> categoria) {
        this.categoria = categoria;
    }

    public void imprimir() {
        System.out.println("Producto (Codigo=" + codigo + ", Nombre=" + nombre + ", Precio=" + precio + ", IVA=" + iva + ", Stock Minimo=" + stockMinimo + ", Stock=" + stock + ", Foto=" + foto + ", Usuario Crea=" + usuarioCrea + ", Fecha Crea=" + fechaCrea + ", Usuario Modif=" + usuarioModif + ", Fecha Modif=" + fechaModif + ", Descripcion=" + descripcion + ", Categoria=" + categoria + ')');
    }

    @Override
    public String toString() {
        return "Producto (Codigo=" + codigo + ", Nombre=" + nombre + ", Precio=" + precio + ", IVA=" + iva + ", Stock Minimo=" + stockMinimo + ", Stock=" + stock + ", Foto=" + foto + ", Usuario Crea=" + usuarioCrea + ", Fecha Crea=" + fechaCrea + ", Usuario Modif=" + usuarioModif + ", Fecha Modif=" + fechaModif + ", Descripcion=" + descripcion + ", Categoria=" + categoria + ')';

    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.codigo;
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
        final Producto other = (Producto) obj;
        return this.codigo == other.codigo;
    }

    
    
}
