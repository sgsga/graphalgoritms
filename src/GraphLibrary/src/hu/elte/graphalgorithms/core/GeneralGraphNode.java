/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.exceptions.KeyAlreadyExistsException;
import java.util.HashMap;

/**
 *
 * @author nagysan
 */
public class GeneralGraphNode {

    private Integer id;
    private boolean idBlank = true;
    private HashMap<String, OwnedObjectConainer> additionalData;
    

    public void setId(Integer id) throws IdAlreadySetException {
        if (idBlank) {
            this.id = id;
        } else {
            throw new IdAlreadySetException();
        }
    }
    
    public void addData(String key, Object o, Object owner) throws KeyAlreadyExistsException {
        if (additionalData == null) {
            additionalData = new HashMap<>();
        }
        if (!additionalData.containsKey(key)){
            additionalData.put(key, new OwnedObjectConainer(o, owner));
        } else {
            throw new KeyAlreadyExistsException();
        }
    }
    
    public Object getData(String key) {
        if (additionalData != null) {
            if (additionalData.containsKey(key)){
                return additionalData.get(key).data;
            } else {
                return null;
            }
        } else return null;
    }
    public void removeData(String key, Object owner) throws SecurityException{
        if (additionalData != null) {
            if (additionalData.containsKey(key) && additionalData.get(key).owner == owner){
                additionalData.remove(key);
            } else {
                throw new SecurityException("Only the owner be able to delete the data!");
            }
        }
    }

    public Integer getId() {
        return id;
    }
    
    
/*                            Inner classes section                           */
    private class OwnedObjectConainer{
        private Object data;
        private Object owner;

        public Object getData() {
            return data;
        }

        public Object getOwner() {
            return owner;
        }

        public OwnedObjectConainer(Object data, Object owner) {
            this.data = data;
            this.owner = owner;
        }
        
        
    }
            
}
