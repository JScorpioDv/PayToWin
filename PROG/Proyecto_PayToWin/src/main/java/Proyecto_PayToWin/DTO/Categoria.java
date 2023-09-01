/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class Categoria {
    
    //ATRIBUTOS 
    private int codigo;
    private String nombre;
    private String descripcion;
    
    //CONSTRUCTORES

    public Categoria(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    //METODES
    public ArrayList<Producto> getProductos(){
        //Retorna un ArrayList de Producto de todos lo productos que tiene esta categoria.
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Categoria other = (Categoria) obj;
        return this.codigo == other.codigo;
    }
    
    
    public void imprimir(){
        System.out.println("Categoria (Codigo= " + codigo + ", Nombre= " + nombre + ", descripcion=" + descripcion + ')');
    }

    @Override
    public String toString() {
        return "Categoria{" + "codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

    

    
    
}
