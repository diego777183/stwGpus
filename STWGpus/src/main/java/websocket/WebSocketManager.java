package websocket;

import bd.DatosNodo;
import bd.DatosNodoDAO;
import bd.EficienciaDTO;
import bd.HashrateDTO;
import bd.PowerDTO;
import bd.PrecioEthereum;
import bd.PrecioEthereumDAO;
import bd.PrecioLuz;
import bd.PrecioLuzDAO;
import bd.TemperaturaDTO;
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
                sendMessageSession(gson.toJson(listaPreciosLuz, List.class), "datePrecioLuzResult", sesion, tipo);
                break;
            case "\"eth\"":
                List<PrecioEthereum> listaPreciosEth = precioEthDB.obtenerPreciosEth();
                sendMessageSession(gson.toJson(listaPreciosEth, List.class), "datePrecioEthResult", sesion, tipo);
                break;
            case "\"temp\"":
                List<DatosNodo> listaDatosTemp =datosNodoDB.obtenerDatosNodo();
                List<TemperaturaDTO> listaTempDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosTemp) {
                    listaTempDTO.add(new TemperaturaDTO(datosNodo.getFecha(), datosNodo.getTemperature()));
                }
                sendMessageSession(gson.toJson(listaTempDTO, List.class), "listaDatosTemp", sesion, tipo);
                break;             
            case "\"efi\"":
                List<DatosNodo> listaDatosEfi = datosNodoDB.obtenerDatosNodo();
                List<EficienciaDTO> listaEfiDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosEfi) {
                    listaEfiDTO.add(new EficienciaDTO(datosNodo.getFecha(), datosNodo.getEfficiency()));
                }
                sendMessageSession(gson.toJson(listaEfiDTO, List.class), "listaDatosEfi", sesion, tipo);
                break;             
            case "\"watt\"":
                List<DatosNodo> listaDatosPower = datosNodoDB.obtenerDatosNodo();
                List<PowerDTO> listaPowerDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosPower) {
                    listaPowerDTO.add(new PowerDTO(datosNodo.getFecha(), datosNodo.getPower()));
                }
                sendMessageSession(gson.toJson(listaPowerDTO, List.class), "listaDatosPower", sesion, tipo);
                break;             
            case "\"hash\"":
                List<DatosNodo> listaDatosHash = datosNodoDB.obtenerDatosNodo();
                List<HashrateDTO> listaHashDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosHash) {
                    listaHashDTO.add(new HashrateDTO(datosNodo.getFecha(), datosNodo.getHashrate()));
                }
                sendMessageSession(gson.toJson(listaHashDTO, List.class), "listaDatosHash", sesion, tipo);
                break;                  
        }
        return message;
    }
    
 
    @OnClose
    public void onClose(Session _session) {

    }

    private void sendMessageSession(String cadena, String comando, Session sesion, String tipo) {
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
