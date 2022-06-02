/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.bd;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fsern
 */
@Stateless

public class PrecioEthereumDAO extends AbstractFacade<PrecioEthereum> {

    @PersistenceContext(unitName = "stwGpusResource")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrecioEthereumDAO(){
        super (PrecioEthereum.class);
    }
    
    
    /**
     * Comprueba si el producto indicado figura en algún pedido.
     * @param _producto
     * @return 
     *//*
    public boolean existePedidoDeProducto (Producto _producto){
        boolean existe = false;
        Query query = em.createQuery("SELECT p FROM Pedido p WHERE p.producto.id=:idProducto");
        query.setParameter("idProducto", _producto.getId());
        if (query.getResultList().size() > 0){
            existe = true;
        }
        return existe;
    }
    */
}
