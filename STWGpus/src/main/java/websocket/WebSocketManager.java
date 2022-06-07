package websocket;

import bd.PrecioEthereumDAO;
import bd.PrecioLuz;
import bd.PrecioLuzDAO;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import java.io.StringReader;
import java.text.ParseException;
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

    @EJB PrecioLuzDAO precioLuzDB; 
    @EJB PrecioEthereumDAO precioEthDB; 

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
            case "\"light\"":
                System.out.println("A fernando no le guta eto");
                List<PrecioLuz> listaPrecios = precioLuzDB.obtenerPreciosLuz();
                sendMessageSession(gson.toJson(listaPrecios,List.class), "datePrecioLuzResult", sesion);
                break;
            case "\"eth\"":
                System.out.println("A fernando si le guta eto");
                break;

        }

        return message;
    }

    @OnClose
    public void onClose(Session _session) {

    }

    private void sendMessageSession(String cadena, String comando, Session sesion){
        try {
            String json =  "{\"cmnd\": \""+ comando + "\", \"values\": " + cadena  + " }";
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
