
package servlet;

import bd.Usuario;
import bd.UsuarioFacade;
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
 * @author Diego Santome
 */
@WebServlet(name = "ModificarUsuario", urlPatterns = {"/modificarUsuario"})
public class ModificarUsuario extends HttpServlet {

    @EJB UsuarioFacade usuarioDAO;
    
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
// <<=== NECESARIO para que funcionen las tildes, Ñs, etc... 
        HttpSession session = request.getSession();
        
        Long id = (Long)(session.getAttribute("idUsuario"));
        
        if (id!=null){
            String login    = request.getParameter("login");
            String pwd      = request.getParameter("pwd");
            String nombre   = request.getParameter("nombre");
            String ap1      = request.getParameter("ap1");
            
            Usuario u = usuarioDAO.find(id);
            if (u!=null){
                u.setId(id);
                u.setLogin(login);
                u.setPassword(pwd);
                u.setNombre(nombre);
                u.setAp1(ap1);
                usuarioDAO.edit(u);

            }

            request.getSession().setAttribute("msg", "El usuario '"+u.getLogin()+"' ha sido modificado.");
            response.sendRedirect(response.encodeURL("verMensajesRecibidos.jsp"));
        
        }else{
            session.setAttribute("msg", "ERROR: La sesión ha caducado.");
            response.sendRedirect(response.encodeURL("index.jsp"));
        }
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
