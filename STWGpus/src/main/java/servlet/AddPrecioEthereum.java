/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.servlet;

import demo.bd.Cliente;
import demo.bd.ClienteDAO;
import demo.bd.PrecioEthereum;
import demo.bd.PrecioEthereumDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 *
 * @author fsern
 */
@WebServlet(name = "AddPrecioEthereum", urlPatterns = {"/addPrecioEthereum"})
public class AddPrecioEthereum extends HttpServlet {

    @EJB PrecioEthereumDAO precioEthDB;
    
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

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run(){
                PrecioEthereum p = new PrecioEthereum();

                try{
                p.setFecha(System.currentTimeMillis());                    
                p.setPrecio(p.getPrecio());
                
                }catch(Exception e){
                    e.printStackTrace();
                }
                precioEthDB.create(p);
            }
        };        
        
        timer.schedule(timerTask, 10, 1000);//segundo parametro, cada cuando empieza, segundo parametro, cada cuanto lo repite
               
       // no vamos a ninguna pagina, por lo que la siguiente linea no hace falta
       //response.sendRedirect(response.encodeRedirectURL("menuClientes.jsp"));
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
            Logger.getLogger(AddPrecioEthereum.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddPrecioEthereum.class.getName()).log(Level.SEVERE, null, ex);
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

}