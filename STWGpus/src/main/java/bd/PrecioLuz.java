/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
public class PrecioLuz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long fecha;
    
    //

    private Integer numUnidades;
    private Double precio;




    public final static String URL = "https://api.preciodelaluz.org/v1/prices/now?zone=PCB";
    
public double obtenerPrecioAPI() throws IOException, InterruptedException{
    double precioLuz;

    HttpClient httpClient = HttpClient.newBuilder().
                    version(HttpClient.Version.HTTP_2).build();
    
    HttpRequest request = HttpRequest.newBuilder().
                    GET().uri(URI.create(URL)).build();
    
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
    JsonObject json = jsonReader.readObject();
    
    BigDecimal precioObtenido = (BigDecimal) json.get("price");
    
    System.out.println("Precio Luz: " + precioObtenido);
    
    precioLuz = precioObtenido.doubleValue();

    return precioLuz;
}    
    
    public Double getPrecio() throws IOException, InterruptedException {
    return obtenerPrecioAPI();
}    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }


    
    public Integer getNumUnidades() {
        return numUnidades;
    }

    public void setNumUnidades(Integer numUnidades) {
        this.numUnidades = numUnidades;
    }


    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

 

    @Override
    public String toString() {
        return "demo.bd.Pedido[ id=" + id + " ]";
    }
    
}
