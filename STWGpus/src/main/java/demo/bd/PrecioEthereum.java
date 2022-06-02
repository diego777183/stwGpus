/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.bd;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author fserna
 */
@Entity
public class PrecioEthereum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long fecha;
    private Producto producto;
    private Integer numUnidades;
    private Double precio;
    @ManyToOne
    private Cliente cliente;

    public final static String URL = "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd";
    
    private Date fechaPrecio;

    //Constructor usado en addprecioethereumdao
    public PrecioEthereum(Double precio, Long fechaPrecio) {
        this.precio = precio;
        this.fecha = fecha;
    }

    public PrecioEthereum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        
    public double obtenerPrecioAPI() throws IOException, InterruptedException{
        HttpClient httpClient = HttpClient.newBuilder().
                        version(HttpClient.Version.HTTP_2).build();
        
        HttpRequest request = HttpRequest.newBuilder().
                        GET().uri(URI.create(URL)).build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
/*
        JsonObject json = new JsonObject(response.body());
        JsonObject json2 = (JsonObject) json.get("ethereum");

        BigDecimal precioObtenido = (BigDecimal) json2.get("usd");
        System.out.println("Precio Ethereum: " + precioObtenido);
        double precio = precioObtenido.doubleValue();
*/
        double precio = 2.53;
        return precio;
    }
   
    public Double getPrecio() throws IOException, InterruptedException {
        return obtenerPrecioAPI();
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public Integer getNumUnidades() {
        return numUnidades;
    }

    public void setNumUnidades(Integer numUnidades) {
        this.numUnidades = numUnidades;
    }
    
    
    
    
    
    
    
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.bd.Pedido[ id=" + id + " ]";
    }
    
}
