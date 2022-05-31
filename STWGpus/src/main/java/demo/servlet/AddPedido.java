/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.servlet;

import demo.bd.Cliente;
import demo.bd.ClienteDAO;
import demo.bd.Pedido;
import demo.bd.Producto;
import demo.bd.ProductoDAO;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fserna
 */
@WebServlet(name = "AddPedido", urlPatterns = {"/addPedido"})
public class AddPedido extends HttpServlet {

    @EJB ClienteDAO clienteDB;
    @EJB ProductoDAO productoDB;

    
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
        
        request.setCharacterEncoding("UTF-8"); // <<=== NECESARIO para que funcionen las tildes, Ñs, etc... 


        Long idCliente      = Long.valueOf(request.getParameter("idCliente"));
        Long idProducto     = Long.valueOf(request.getParameter("idProducto"));
        String txtNumUnidades = request.getParameter("numUnidades");
        Integer numUnidades = null;
        
        Cliente cliente     = clienteDB.find(idCliente);
        Producto producto   = productoDB.find(idProducto);

        if ((cliente!=null) && (producto!=null)){
            if ((txtNumUnidades==null)||(txtNumUnidades.length()<=0)){
                request.getSession(true).setAttribute("mensaje", "ERROR: Debes teclear el número de unidades que deseas comprar.");
            }else{
                numUnidades = Integer.valueOf(request.getParameter("numUnidades"));
                if (numUnidades<=0){
                    request.getSession().setAttribute("mensaje", "ERROR: El número de unidades debe ser un valor positivo.");
                }else{            
                    Pedido pedido = new Pedido();
                    pedido.setFecha(System.currentTimeMillis());
                    pedido.setNumUnidades(numUnidades);
                    pedido.setCliente(cliente);
                    pedido.setProducto(producto);
                    pedido.setPrecio(numUnidades * producto.getPrecioUnitario());

                    cliente.addPedido(pedido);
                    cliente.setSaldo(cliente.getSaldo()-pedido.getPrecio());
                    clienteDB.edit(cliente);
                }
            }
        }else{
            request.getSession(true).setAttribute("mensaje", "ERROR: Debe indicarse un cliente y un producto.");
        }
    
        response.sendRedirect(response.encodeURL("menuPedidosCliente.jsp?id="+cliente.getId()));
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
