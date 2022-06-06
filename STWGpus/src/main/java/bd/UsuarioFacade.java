/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;



import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fsern
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    
    
    
    public Usuario checkAutenticacion (String _login, String _pwd){
        Usuario usuario = null;
        
        if ((this.findAll().isEmpty())  &&
            (_login.equals("admin"))    &&
            (_pwd.equals("admin"))){
                usuario = new Usuario();
                usuario.setLogin("admin");
                usuario.setPassword("istrador");
                usuario.setNombre("Adminis");
                usuario.setAp1("trador");
                usuario.setSaldo(0.0);
                this.create(usuario);

        }else{
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login=:login AND u.password=:pwd");
            query.setParameter("login", _login);
            query.setParameter("pwd", _pwd);

            try{
                usuario = (Usuario) query.getSingleResult();
            }catch(Exception e){
            }
        }
        return usuario;
    }
    
    /**
     * Construye un combo html con todos los clientes existentes
     * @param _id  identificador del combo, que será usado en el servlet que lo utilice.
     * @param _msg mensaje inicial que mostrará el combo
     * @return 
     */
    public String getComboBoxDestinos(String _id, String _msg){
        String html = "<select id=\""+_id+"\" name=\""+_id+"\">\n";
        html += "<option value=\"0\" SELECTED>"+_msg+"</option>\n";
        for (Usuario c: findAll()){
            html+="<option value=\""+c.getId()+"\">"+c.getNombre()+" "+c.getAp1()+"</option>\n";
        }
        html += "</select>";
        
        return html;
    }
    
  
    
}
