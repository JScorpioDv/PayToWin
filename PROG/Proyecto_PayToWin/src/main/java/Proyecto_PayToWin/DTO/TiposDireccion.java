/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

/**
 *
 * @author ciclost
 */
public enum TiposDireccion {

    Envío("Envío"), Facturación("Facturación");
    
    //ATRIBUTS
    private String id;
    
    //CONSTRUCTOR
    
    private TiposDireccion(String id) {
        this.id = id;
    }
    
    
    //METODES

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
