/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import java.io.Serializable;

/**
 *
 * @author nagysan
 */
public class GeneralGraphArc implements Serializable {

    private Integer id;
    private boolean initialized = false;
    private Integer fromId;
    private Integer toId;
    private Float cost;

    private final void setId(Integer id) {
        this.id = id;
    }

    public boolean isInitialized() {
        return initialized;
    }

    
    
    public final void setCost(Float cost) {
        this.cost = cost;
    }

    
    public final void initialize(Integer fromId, Integer toId, Float cost, Integer id) {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
        setId(id);
        initialized = true;
    }

    public final Integer getId() {
        return id;
    }

    public final Integer getFromId() {
        return fromId;
    }

    public final Integer getToId() {
        return toId;
    }

    public final Float getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "GeneralGraphArc(" + "id=" + id +")|"+ fromId + " == ("+cost+") ==> " + toId;
    }
    
    
}
