/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import bd.AbstractFacade;
import java.text.SimpleDateFormat;
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
 * @author Fernando Revilla
 */
@Stateless

public class PrecioLuzDAO extends AbstractFacade<PrecioLuz> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrecioLuzDAO() {
        super(PrecioLuz.class);
    }

    public List<PrecioLuz> obtenerPreciosLuz(Date fecha) {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        System.out.println("HOLA");
        Query query = em.createQuery("SELECT e FROM PrecioLuz e"); 
        System.out.println("adios");

        List<PrecioLuz> datosLuz = query.getResultList();
        List<PrecioLuz> auxDatosEthereum = new ArrayList();

        Calendar cal1 = Calendar.getInstance(); 
        Calendar cal2 = Calendar.getInstance(); 
                
        for (PrecioLuz d : datosLuz) {
            System.out.println("f: " + d.toString());
            cal1.setTime(d.getFecha()); 
            cal2.setTime(fecha); 
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) 
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
                
                auxDatosEthereum.add(d);
            }
        }
        return auxDatosEthereum;
    }

}
