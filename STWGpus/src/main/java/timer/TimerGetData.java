
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
 * @author Diego Santome, Alberto Perez y Fernando _Revilla
 */
@Stateless
public class TimerGetData {

    private static final String BASE_URI = "http://88.6.76.114:4000/get";
    @EJB
    PrecioEthereumDAO precioEthDB;
    @EJB
    PrecioLuzDAO precioLuzDB;
    @EJB
    DatosNodoDAO datosNodoDB;

    public final static String URL_ETHEREUM = "https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd";
    public final static String URL_ENERGY = "https://api.preciodelaluz.org/v1/prices/now?zone=PCB";

    @Schedule(hour="*", minute="*/5", persistent = false)

    public void myTimer() throws IOException, InterruptedException {
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
        return Double.parseDouble(json2.get("usd").toString());
    }

    private void addLightPrice() throws IOException, InterruptedException {
        PrecioLuz p = new PrecioLuz();
        p.setFecha();
        p.setPrecio(obtenerPrecioLuzAPI());
        try {
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
