/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Vol;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cast
 */
@Stateless
public class VolFacade extends AbstractFacade<Vol> {

    @PersistenceContext(unitName = "PROJET_JSF_AVIONPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolFacade() {
        super(Vol.class);
    }
    
}
