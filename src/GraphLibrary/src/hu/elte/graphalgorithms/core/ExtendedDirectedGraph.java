/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.HashMap;

/**
 *
 * @author nagysan
 */
public class ExtendedDirectedGraph<N extends GeneralGraphNode, A extends GeneralGraphArc> extends DirectedGraph<N, A>{
    public String toJSON(){
        Gson gson = new Gson();
        HashMap<String,Object> data = new HashMap<>();
        data.put("nodes", nodeDatas.keySet());
        HashMap<Integer,HashMap<Integer,Float>> arcTemp = new HashMap<>();
        for (A arc : arcDatas.values()){
            
        }
        data.put("arcs" , arcDatas.keySet());
        data.put("graph", graph);
        data.put("arcSequence", arcSequence);
        data.put("nodeSequence", nodeSequence);
        return gson.toJson(data);
    }
    
    public void fromJSON(String jsonData, Graph.NodeArcFactory<N,A> factory) {
        Gson gson = new Gson();
        ExtendedDirectedGraph<N,A> result = new ExtendedDirectedGraph<>();
        HashMap<String, Object> fromJson = gson.fromJson(jsonData, HashMap.class);
        arcSequence = ((Double)fromJson.get("arcSequence")).intValue();
        nodeSequence = ((Double)fromJson.get("nodeSequence")).intValue();
        
    }
}
