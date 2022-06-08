package websocket;

import bd.DatosNodo;
import bd.DatosNodoDAO;
import bd.PrecioEthereum;
import bd.PrecioEthereumDAO;
import bd.PrecioLuz;
import bd.PrecioLuzDAO;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author fserna
 */
@Stateless
@ServerEndpoint("/stwGpus")
public class WebSocketManager {

    private Session session; // última session establecida. No sabemos a quién pertenece.
    // en realidad solo la queremos para poder usar el 
    // método "getOpenSessions()"

    @EJB
    PrecioLuzDAO precioLuzDB;
    @EJB
    PrecioEthereumDAO precioEthDB;
    @EJB
    DatosNodoDAO datosNodoDB;

    Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session _session) {
        this.session = _session;
    }

    @OnMessage
    public String onMessage(Session sesion, String message) {
        System.out.println("[!] Recibo ===  de la sesion " + sesion.getId() + " el mensaje " + message);
        JsonReader jsonReader = Json.createReader(new StringReader(message));
        JsonObject obj = jsonReader.readObject();
        String accion = "";
        String jsonCadena = "";
        String valueFecha = "";

        String tipo = obj.get("values").toString();
        System.out.println(tipo);
        switch (tipo) {
            case "\"luz\"":
                List<PrecioLuz> listaPreciosLuz = precioLuzDB.obtenerPreciosLuz();
                sendMessageSession(gson.toJson(listaPreciosLuz, List.class), "datePrecioLuzResult", sesion);
                break;
            case "\"eth\"":
                List<PrecioEthereum> listaPreciosEth = precioEthDB.obtenerPreciosEth();
                sendMessageSession(gson.toJson(listaPreciosEth, List.class), "datePrecioEthResult", sesion);
                break;
            case "\"temp\"":
                List<Integer> listaDatosTemp = parseNodeData(tipo,datosNodoDB.obtenerDatosNodo(tipo));
                sendMessageSession(gson.toJson(listaDatosTemp, List.class), "listaDatosTemp", sesion);
                break;             
            case "\"efi\"":
                List<Integer> listaDatosEfi = parseNodeData(tipo,datosNodoDB.obtenerDatosNodo(tipo));
                sendMessageSession(gson.toJson(listaDatosEfi, List.class), "listaDatosEfi", sesion);
                break;             
            case "\"watt\"":
                List<Integer> listaDatosPower = parseNodeData(tipo,datosNodoDB.obtenerDatosNodo(tipo));
                sendMessageSession(gson.toJson(listaDatosPower, List.class), "listaDatosPower", sesion);
                break;             
            case "\"hash\"":
                List<Double> listaDatosHash = parseNodeData(tipo,datosNodoDB.obtenerDatosNodo(tipo));
                sendMessageSession(gson.toJson(listaDatosHash, List.class), "listaDatosHash", sesion);
                break;                  
        }
        return message;
    }
    
    private List parseNodeData(String tipo, List<String> lista){
        switch (tipo) {
            case "\"temp\"":       
            case "\"watt\"":
                List<Integer> datosTempPow = new ArrayList<Integer>();
                for (String e : lista) {
                    if(e.equals("")){
                        datosTempPow.add(0);                        
                    }else{
                        datosTempPow.add(Integer.parseInt(e));                        
                    }
                }
                return datosTempPow;             
            case "\"hash\"":
                List<Double> datosHash = new ArrayList<Double>();
                for (String e : lista) {
                    if(e.equals("")){
                        datosHash.add(0.00);
                    }else{
                        datosHash.add((Integer.parseInt(e)/1000000.00));
                    }
                }
                return datosHash;     
            case "\"efi\"": 
                List<Integer> datosEfi = new ArrayList<Integer>();
                for (String e : lista) {
                    if(e.equals("")){
                        datosEfi.add(0);
                    }else{
                        String replaced = e.replaceAll("\"", "").replaceAll("kH/W", "");
                        datosEfi.add(Integer.parseInt(replaced));                        
                    }
                }
                return datosEfi;     
        }        
        return null;
    }

    @OnClose
    public void onClose(Session _session) {

    }

    private void sendMessageSession(String cadena, String comando, Session sesion) {
        try {
            String json = "{\"cmnd\": \"" + comando + "\", \"values\": " + cadena + " }";
            System.out.println("El JSON q se manda es " + json);
            sesion.getBasicRemote().sendText(json);
        } catch (IOException ex) {
            Logger.getLogger(WebSocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void publishUsuariosRegistrados(List<String> _uu) {
        String json = "{\"cmd\": \"usuarios\", \"registrados\": [";

        int n = 0;
        for (String u : _uu) {
            if (n > 0) {
                json += ", ";
            }
            n++;
            json += "\"" + u + "\"";
        }
        json += "]}";
        System.out.println(json);

        if (session != null) {
            try {
                for (Session s : session.getOpenSessions()) {
                    s.getBasicRemote().sendText(json);
                }
            } catch (IOException ex) {
            }
        }
    }
}
