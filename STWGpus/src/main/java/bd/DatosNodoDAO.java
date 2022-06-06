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
    

    
}
