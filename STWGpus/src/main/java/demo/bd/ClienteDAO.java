/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.bd;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fsern
 */
@Stateless
public class ClienteDAO extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteDAO() {
        super(Cliente.class);
    }
    
    
    
    
    /**
     * Construye un combo html con todos los clientes existentes
     * @param _id  identificador del combo, que será usado en el servlet que lo consuma.
     * @return 
     */
    public String getComboBox(String _id){
        String html = "<select id=\""+_id+"\" name=\""+_id+"\">\n";
        html += "<option value=\"0\" SELECTED>Selecciona un Cliente...</option>\n";
        for (Cliente c: findAll()){
            html+="<option value=\""+c.getId()+"\">"+c.getNombre()+" "+c.getAp1()+"</option>\n";
        }
        html += "</select>";
        
        return html;
    }
    
}
