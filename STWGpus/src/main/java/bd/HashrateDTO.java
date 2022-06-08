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
public class HashrateDTO {

    private Date fecha;
    private double precio;

    public HashrateDTO(Date fecha, String precio) {
        this.fecha = fecha;
        if (precio.equals("")) {
            this.precio = 0.00;
        } else {
            int precioOriginal = Integer.valueOf(precio);
            this.precio = precioOriginal / 1000000.00;
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

}
