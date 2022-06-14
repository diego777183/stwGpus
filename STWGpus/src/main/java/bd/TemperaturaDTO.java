
package bd;

import java.util.Date;

/**
 *
 * @author Fernando Revilla
 */
public class TemperaturaDTO {
    private Date fecha;
    private int precio;

    public TemperaturaDTO(Date fecha, String precio) {
        this.fecha = fecha;
        
        if(precio.equals("")){
            this.precio = 0;
        }else{
            this.precio = Integer.valueOf(precio);
        }
    }
}
