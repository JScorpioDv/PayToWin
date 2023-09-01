/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class Usuario {
    
    //ATRIBUTOS
    private String nombreUsuario; //CLAVE
    private String nombreReal;
    private String correo; 
    private String passw;
    private LocalDate fnac;
    private int tel;
    private LocalDateTime ultconex;
    private String foto;
    private TiposUsuario tipo;
    
    //CONSTRUCTORES
    public Usuario(String nombreUsuario, String nombreReal, String correo, String passw, LocalDate fnac, int tel, LocalDateTime ultconex, String foto, TiposUsuario tipo) {
        this.nombreUsuario = nombreUsuario;
        this.nombreReal = nombreReal;
        this.correo = correo;
        this.passw = passw;
        this.fnac = fnac;
        this.tel = tel;
        this.ultconex = ultconex;
        this.foto = foto;
        this.tipo = tipo;
    }
    
    //GETTERS Y SETTERS

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public LocalDate getFnac() {
        return fnac;
    }

    public void setFnac(LocalDate fnac) {
        this.fnac = fnac;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public LocalDateTime getUltconex() {
        return ultconex;
    }

    public void setUltconex(LocalDateTime ultconex) {
        this.ultconex = ultconex;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TiposUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TiposUsuario tipo) {
        this.tipo = tipo;
    }
    
    //METODOS
    public boolean esCliente(){
        return this.tipo == TiposUsuario.Cliente;
    }
    
    public boolean esAdmin(){
        return this.tipo == TiposUsuario.Admin;
    }
    
    public boolean esAnonimo(){
        return this.tipo == null;
    }
    
    
    public void imprimir(){
        System.out.println("Usuario (Nombre Usuario= " + nombreUsuario + ", Nombre Real= " + nombreReal + ", Correo= " + correo + ", Contraseña= " + passw + ", fNac= " + fnac + ", Telefono= " + tel + ", UltConex= " + ultconex + ", Foto= " + foto + ", Tipo= " + tipo + ')');
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.nombreUsuario);
        hash = 53 * hash + Objects.hashCode(this.correo);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        return Objects.equals(this.correo, other.correo);
    }

    @Override
    public String toString() {
        return "Usuario (Nombre Usuario= " + nombreUsuario + ", Nombre Real= " + nombreReal + ", Correo= " + correo + ", Contraseña= " + passw + ", fNac= " + fnac + ", Telefono= " + tel + ", UltConex= " + ultconex + ", Foto= " + foto + ", Tipo= " + tipo + ')';
    }
    
    
}
