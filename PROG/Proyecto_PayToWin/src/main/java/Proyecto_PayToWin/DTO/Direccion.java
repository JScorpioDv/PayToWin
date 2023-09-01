/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;


/**
 *
 * @author juanp
 */
public class Direccion {
    
    //ATRIBUTOS
    private int codigo; //CLAVE
    private String calle;
    private String ciudad;
    private String provincia;
    private int cp;
    private String pais;
    private Usuario usuario;
    private TiposDireccion tipo;
    
    //CONSTRUCTORES

    public Direccion(int codigo, String calle, String ciudad, String provincia, int cp, String pais, Usuario usuario, TiposDireccion tipo) {
        this.codigo = codigo;
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.cp = cp;
        this.pais = pais;
        this.usuario = usuario;
        this.tipo = tipo;
    }
    
    
    //GETTERS Y SETTERS

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TiposDireccion getTipo() {
        return tipo;
    }

    public void setTipo(TiposDireccion tipo) {
        this.tipo = tipo;
    }
    
    //METODES

    public void imprimir(){
        System.out.println("Direccion (Codigo=" + codigo + ", Calle=" + calle + ", Ciudad=" + ciudad + ", Provincia=" + provincia + ", Codigo Postal=" + cp + ", Pais=" + pais + ", Usuario=" + usuario + ", Tipo=" + tipo + ")");
    }

    @Override
    public String toString() {
        return "Direccion (Codigo=" + codigo + ", Calle=" + calle + ", Ciudad=" + ciudad + ", Provincia=" + provincia + ", Codigo Postal=" + cp + ", Pais=" + pais + ", Usuario=" + usuario + ", Tipo=" + tipo + ")";
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.codigo;
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
        final Direccion other = (Direccion) obj;
        return this.codigo == other.codigo;
    }
    
    public boolean perteneceA(Usuario u){
        return this.usuario.equals(u);
    }

    
    
    
    
    
}
