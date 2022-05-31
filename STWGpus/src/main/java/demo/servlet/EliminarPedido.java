
package demo.servlet;

import demo.bd.Cliente;
import demo.bd.ClienteDAO;
import demo.bd.Pedido;
import demo.bd.PedidoDAO;
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
@WebServlet(name = "EliminarPedido", urlPatterns = {"/eliminarPedido"})
public class EliminarPedido extends HttpServlet {

    @EJB PedidoDAO pedidoDB;
    @EJB ClienteDAO clienteDB;
    
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
        
        Long idCliente = Long.valueOf(request.getParameter("idCliente"));
        Long idPedido = Long.valueOf(request.getParameter("idPedido"));
        
        Pedido p  = pedidoDB.find(idPedido);
        if (p !=null){
            try{
                Cliente cliente = p.getCliente();
                cliente.removePedido(p);
                
                cliente.setSaldo(cliente.getSaldo()+p.getPrecio());
                clienteDB.edit(cliente);
                
                pedidoDB.remove(p);
            }catch(javax.persistence.PersistenceException e){
                session.setAttribute("msg", "ERROR: No puede eliminarse este pedido.");
            }
        }else{
            session.setAttribute("msg", "ERROR: No puede eliminarse un pedido que no existe.");
        }
        
        response.sendRedirect(response.encodeURL("menuPedidosCliente.jsp?id="+idCliente));
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
