/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fsern
 */
@Stateless

public class DatosNodoDAO extends AbstractFacade<DatosNodo> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosNodoDAO() {
        super(DatosNodo.class);
    }

    public List<String> obtenerDatosNodo(String tipo) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        Query query = null;
        
        switch (tipo) {
            case "\"temp\"":
                query = em.createQuery("SELECT e.temperature FROM DatosNodo e"); 
                break;
            case "\"efi\"":
                query = em.createQuery("SELECT e.efficiency FROM DatosNodo e"); 
                break;
            case "\"watt\"":
                query = em.createQuery("SELECT e.power FROM DatosNodo e"); 
                break;
            case "\"hash\"":
                query = em.createQuery("SELECT e.hashrate FROM DatosNodo e"); 
                break;                  
        }
        
        return query.getResultList();
    }
    

    
}
