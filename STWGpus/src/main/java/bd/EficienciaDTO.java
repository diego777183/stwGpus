/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd;

import java.util.Date;

/**
 *
 * @author Diego
 */
public class EficienciaDTO {
    private Date fecha;
    private int precio;

    public EficienciaDTO(Date fecha, String precio) {
        this.fecha = fecha;
        
        if(precio.equals("")){
            this.precio = 0;
        }else{
            this.precio = Integer.valueOf(precio.replaceAll("kH/W", "").replaceAll("\"", ""));
        }
    }
}
