
package servlet;

import bd.Usuario;
import bd.UsuarioFacade;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego Santome
 */
@WebServlet(name = "getTemperatura", urlPatterns = {"/getTemperatura"})
public class ActualizarTemperatura extends HttpServlet {

    private static final String BASE_URI = "http://192.168.1.59:4000/get/temp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {

        request.setCharacterEncoding("UTF-8"); // <<=== NECESARIO para que funcionen las tildes, Ñs, etc... 

        request.setAttribute("temperaturaActual", obtenerTemperatura());
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(ActualizarTemperatura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(ActualizarTemperatura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String obtenerTemperatura() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder().
                GET().uri(URI.create(BASE_URI)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
        JsonObject json = jsonReader.readObject();
        double temp = Double.valueOf(json.get("thermometer").toString().replaceAll("\"", "")) / 1000;

        return (String.format("%.2f", temp) + " ºC");
    }

}
