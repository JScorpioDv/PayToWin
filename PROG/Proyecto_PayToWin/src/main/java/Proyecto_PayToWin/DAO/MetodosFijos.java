/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author juanp
 * @param <T>
 */
public interface MetodosFijos<T> {
    
    int actualizar(T objeto) throws SQLException;
    int anyadir(T objeto) throws SQLException;
    T eliminar(int codigo) throws SQLException;
    T eliminar(T objeto) throws SQLException;
    boolean existe(T objeto) throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    T getByCodigo(int codigo) throws SQLException;
    int siguienteCodigo() throws SQLException;
    void vaciar() throws SQLException;
}
