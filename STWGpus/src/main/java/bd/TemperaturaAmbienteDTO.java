/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd;

import java.util.Date;
import java.text.DecimalFormat;


/**
 *
 * @author Diego
 */
public class TemperaturaAmbienteDTO {
    private Date fecha;
    private double precio;
    
    
    public TemperaturaAmbienteDTO(Date fecha, String precio) {
        this.fecha = fecha;
        
        if(precio.equals("")){
            this.precio = 0.00;
        }else{
            String pattern = "#.##";
            DecimalFormat decimalFormat =  new DecimalFormat(pattern);
            this.precio = Double.valueOf(decimalFormat.format(Double.valueOf(precio.replaceAll("\"", "")))) / 1000.00;
        }
    }

    @Override
    public String toString() {
        return "TemperaturaAmbienteDTO{" + "fecha=" + fecha + ", precio=" + precio;
    }
}

