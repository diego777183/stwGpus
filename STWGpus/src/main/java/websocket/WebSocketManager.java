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
import bd.TemperaturaAmbienteDTO;
import bd.TemperaturaDTO;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Alberto Perez, Fernando Revilla y Alberto Perez
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
    public String onMessage(Session sesion, String message) throws IOException, InterruptedException, ParseException {
        JsonReader jsonReader = Json.createReader(new StringReader(message));
        JsonObject obj = jsonReader.readObject();
        String accion = "";
        String jsonCadena = "";

        String tipo = obj.get("values").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = obj.get("fecha").toString().replaceAll("\"", "");

        Date valueFecha = sdf.parse(fecha);
        switch (tipo) {
            case "\"luz\"":
                List<PrecioLuz> listaPreciosLuz = precioLuzDB.obtenerPreciosLuz(valueFecha);
                sendMessageSession(gson.toJson(listaPreciosLuz, List.class), "datePrecioLuzResult", sesion);
                break;
            case "\"eth\"":
                List<PrecioEthereum> listaPreciosEth = precioEthDB.obtenerPreciosEth(valueFecha);
                sendMessageSession(gson.toJson(listaPreciosEth, List.class), "datePrecioEthResult", sesion);
                break;
            case "\"temp\"":
                List<DatosNodo> listaDatosTemp = datosNodoDB.obtenerDatosNodo(valueFecha);
                List<TemperaturaDTO> listaTempDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosTemp) {
                    listaTempDTO.add(new TemperaturaDTO(datosNodo.getFecha(), datosNodo.getTemperature()));
                }
                sendMessageSession(gson.toJson(listaTempDTO, List.class), "listaDatosTemp", sesion);
                break;
            case "\"efi\"":
                List<DatosNodo> listaDatosEfi = datosNodoDB.obtenerDatosNodo(valueFecha);
                List<EficienciaDTO> listaEfiDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosEfi) {
                    listaEfiDTO.add(new EficienciaDTO(datosNodo.getFecha(), datosNodo.getEfficiency()));
                }
                sendMessageSession(gson.toJson(listaEfiDTO, List.class), "listaDatosEfi", sesion);
                break;
            case "\"watt\"":
                List<DatosNodo> listaDatosPower = datosNodoDB.obtenerDatosNodo(valueFecha);
                List<PowerDTO> listaPowerDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosPower) {
                    listaPowerDTO.add(new PowerDTO(datosNodo.getFecha(), datosNodo.getPower()));
                }
                sendMessageSession(gson.toJson(listaPowerDTO, List.class), "listaDatosPower", sesion);
                break;
            case "\"hash\"":
                List<DatosNodo> listaDatosHash = datosNodoDB.obtenerDatosNodo(valueFecha);
                List<HashrateDTO> listaHashDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosHash) {
                    listaHashDTO.add(new HashrateDTO(datosNodo.getFecha(), datosNodo.getHashrate()));
                }
                sendMessageSession(gson.toJson(listaHashDTO, List.class), "listaDatosHash", sesion);
                break;
            case "\"tempAmbiente\"":
                List<DatosNodo> listaDatosTempAmbiente = datosNodoDB.obtenerDatosNodo(valueFecha);
                List<TemperaturaAmbienteDTO> listaTempAmbienteDTO = new ArrayList();
                for (DatosNodo datosNodo : listaDatosTempAmbiente) {
                    listaTempAmbienteDTO.add(new TemperaturaAmbienteDTO(datosNodo.getFecha(), datosNodo.getThermometer()));
                }
                sendMessageSession(gson.toJson(listaTempAmbienteDTO, List.class), "listaDatosTempAmbiente", sesion);
                break;
        }
        return message;
    }

    @OnClose
    public void onClose(Session _session) {

    }

    private void sendMessageSession(String cadena, String comando, Session sesion) {
        try {
            String json = "{\"cmnd\": \"" + comando + "\", \"values\": " + cadena + " }";

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
