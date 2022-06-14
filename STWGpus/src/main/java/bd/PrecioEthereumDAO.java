
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
 * @author Alberto Perez
 */
@Stateless

public class PrecioEthereumDAO extends AbstractFacade<PrecioEthereum> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrecioEthereumDAO() {
        super(PrecioEthereum.class);
    }

    public List<PrecioEthereum> obtenerPreciosEth(Date fecha) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date start = c.getTime();
        Query query = em.createQuery("SELECT e FROM PrecioEthereum e"); 
        
        List<PrecioEthereum> datosEthereum = query.getResultList();
        List<PrecioEthereum> auxDatosEthereum = new ArrayList();

        Calendar cal1 = Calendar.getInstance(); 
        Calendar cal2 = Calendar.getInstance(); 
                
        for (PrecioEthereum d : datosEthereum) {
            System.out.println("f: " + d.toString());
            cal1.setTime(d.getFecha()); 
            cal2.setTime(fecha); 
            if (cal1.get(Calendar.DAY_OF_YEAR) == 
                    cal2.get(Calendar.DAY_OF_YEAR) && 
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
                
                auxDatosEthereum.add(d);
            }
        }
        return auxDatosEthereum;
    }
}
