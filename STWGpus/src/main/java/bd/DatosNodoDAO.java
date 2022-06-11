/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.ArrayList;
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

    public List<DatosNodo> obtenerDatosNodo(Date fecha) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        Query query = null;

        query = em.createQuery("SELECT e FROM DatosNodo e");
        
        List<DatosNodo> datosNodo = query.getResultList();
        List<DatosNodo> auxDatosNodo = new ArrayList();

        Calendar cal1 = Calendar.getInstance(); 
        Calendar cal2 = Calendar.getInstance(); 
        

        
        for (DatosNodo d : datosNodo) {
            System.out.println("f: " + d.toString());
            cal1.setTime(d.getFecha()); 
            cal2.setTime(fecha); 
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
                auxDatosNodo.add(d);
            }
        }
        return auxDatosNodo;

    }

}
