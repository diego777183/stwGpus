/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.servlet;

import demo.bd.PedidoDAO;
import demo.bd.Producto;
import demo.bd.ProductoDAO;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fsern
 */
@WebServlet(name = "EliminarProducto", urlPatterns = {"/eliminarProducto"})
public class EliminarProducto extends HttpServlet {

    @EJB ProductoDAO productoDB;
    @EJB PedidoDAO pedidoDB;
    
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
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        
        Long id = Long.valueOf(request.getParameter("id"));
        
        Producto p  = productoDB.find(id);
        if (p !=null){
            try{
                if ( ! pedidoDB.existePedidoDeProducto(p)){
                    productoDB.remove(p);
                }else{
                    session.setAttribute("msg", "ERROR: No puede eliminarse este producto porque figura en un pedido.");
                }
            }catch(javax.persistence.PersistenceException e){
                session.setAttribute("msg", "ERROR: No puede eliminarse este producto.");
            }
        }
        
        response.sendRedirect(response.encodeRedirectURL("menuProductos.jsp"));
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
        processRequest(request, response);
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
        processRequest(request, response);
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
