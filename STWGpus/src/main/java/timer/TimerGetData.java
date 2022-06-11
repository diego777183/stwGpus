/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package timer;

import bd.DatosNodo;
import bd.DatosNodoDAO;
import bd.PrecioEthereum;
import bd.PrecioEthereumDAO;
import bd.PrecioLuz;
import bd.PrecioLuzDAO;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Diego
 */
@Stateless
public class TimerGetData {

    private static final String BASE_URI = "http://192.168.1.59:4000/get";
    @EJB
    PrecioEthereumDAO precioEthDB;
    @EJB
    PrecioLuzDAO precioLuzDB;
    @EJB
    DatosNodoDAO datosNodoDB;

    public final static String URL_ETHEREUM = "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd";
    public final static String URL_ENERGY = "https://api.preciodelaluz.org/v1/prices/now?zone=PCB";

    @Schedule(hour = "*", persistent = false)

    public void myTimer() throws IOException, InterruptedException {
        System.out.println("COJO DATOS");
        addEthereumPrice();
        addNodeData();
        addLightPrice();
    }

    public void addNodeData() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder().
                GET().uri(URI.create(BASE_URI)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
        JsonObject json = jsonReader.readObject();
        DatosNodo datosNodo = new DatosNodo();
        System.out.println(json.get("gpus").toString());
        datosNodo.setFecha();
        
        JsonObject jsonNode = (JsonObject) json.get("gpus");

        if( Integer.parseInt(jsonNode.get("isOnline").toString()) == 0){
            datosNodo.setName("");
            datosNodo.setEfficiency("");
            datosNodo.setHashrate("");
            datosNodo.setPowerGPU("");
            datosNodo.setTemperature("");
            datosNodo.setPower(json.get("power").toString());
            datosNodo.setThermometer(json.get("thermometer").toString());
        }else{
            datosNodo.setName(jsonNode.get("name").toString());
            datosNodo.setEfficiency(jsonNode.get("efficiency").toString());
            datosNodo.setHashrate(jsonNode.get("hashrate").toString());
            datosNodo.setPowerGPU(jsonNode.get("powerGPU").toString());
            datosNodo.setTemperature(jsonNode.get("temperature").toString());
            datosNodo.setPower(json.get("power").toString());
            datosNodo.setThermometer(json.get("thermometer").toString());
        }
        
        try {
            datosNodoDB.create(datosNodo);
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }

    public void addEthereumPrice() throws IOException, InterruptedException {
        PrecioEthereum p = new PrecioEthereum();
        p.setFecha();
        p.setPrecio(obtenerPrecioEthereumAPI());
        try {
            precioEthDB.create(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double obtenerPrecioEthereumAPI() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder().
                GET().uri(URI.create(URL_ETHEREUM)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
        JsonObject json = jsonReader.readObject();
        JsonObject json2 = (JsonObject) json.get("ethereum");
        System.out.println(json2);
        return Double.parseDouble(json2.get("usd").toString());
    }

    private void addLightPrice() throws IOException, InterruptedException {
        PrecioLuz p = new PrecioLuz();
        p.setFecha();
        p.setPrecio(obtenerPrecioLuzAPI());
        try {
            System.out.println("GUARDO LUZ");
            System.out.println(p.getPrecio());
            precioLuzDB.create(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Double obtenerPrecioLuzAPI() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder().
                GET().uri(URI.create(URL_ENERGY)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
        JsonObject json = jsonReader.readObject();

        
        return Double.parseDouble(json.get("price").toString());

    }

}
