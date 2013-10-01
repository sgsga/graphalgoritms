/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;

/**
 *
 * @author nagysan
 */
public class GeneralGraphArc {

    private Integer id;
    private boolean idBlank = true;
    private Integer fromId;
    private Integer toId;
    private Float cost;

    private void setId(Integer id) throws IdAlreadySetException {
        if (idBlank) {
            this.id = id;
        } else {
            throw new IdAlreadySetException();
        }
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    
    public void initialize(Integer fromId, Integer toId, Float cost, Integer id) throws IdAlreadySetException {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
        setId(id);
    }
    
    
}
