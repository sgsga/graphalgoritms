/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import com.google.gson.Gson;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.exceptions.KeyAlreadyExistsException;
import java.util.HashMap;

/**
 *
 * @author nagysan
 */
public class GeneralGraphNode{

    private Integer id;
    private boolean idBlank = true;
    private HashMap<String, OwnedObjectContainer> additionalData;
    

    public final void setId(Integer id) throws IdAlreadySetException {
        if (idBlank) {
            this.id = id;
            idBlank = false;
        } else {
            throw new IdAlreadySetException();
        }
    }
    
    public final void addData(String key, Object o, Object owner) throws KeyAlreadyExistsException {
        if (additionalData == null) {
            additionalData = new HashMap<>();
        }
        if (!additionalData.containsKey(key)){
            additionalData.put(key, new OwnedObjectContainer(o, owner));
        } else {
            throw new KeyAlreadyExistsException();
        }
    }
    
    public final Object getData(String key) {
        if (additionalData != null) {
            if (additionalData.containsKey(key)){
                return additionalData.get(key).data;
            } else {
                return null;
            }
        } else return null;
    }
    public final void removeData(String key, Object owner) throws SecurityException{
        if (additionalData != null) {
            if (additionalData.containsKey(key) && additionalData.get(key).owner == owner){
                additionalData.remove(key);
            } else {
                throw new SecurityException("Only the owner be able to delete the data!");
            }
        }
    }

    public final Integer getId() {
        return id;
    }
    
    public final String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
/*                            Inner classes section                           */
    private final class OwnedObjectContainer{
        private Object data;
        private Object owner;

        public Object getData() {
            return data;
        }

        public Object getOwner() {
            return owner;
        }

        public OwnedObjectContainer(Object data, Object owner) {
            this.data = data;
            this.owner = owner;
        }
        
        
    }

//    @Override
//    protected final Object clone() throws CloneNotSupportedException {
//        GeneralGraphNode g = new GeneralGraphNode();
//        g.id = id;
//        g.idBlank = idBlank;
//        HashMap<String,OwnedObjectContainer> newData = new HashMap<>();
//        g.additionalData = new 
//        return g;
//    }
            
}
