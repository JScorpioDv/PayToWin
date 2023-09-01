/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DTO;

/**
 *
 * @author ciclost
 */
public enum TiposPago {
    
    Tarjeta("Tarjeta"), Paypal("Paypal"), Bizum("Bizum");
    
    //ATRIBUTS
    private String id;
    
    //CONSTRUCTOR

    private TiposPago(String id) {
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
