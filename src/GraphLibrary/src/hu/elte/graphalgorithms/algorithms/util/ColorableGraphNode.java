/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.util;

import hu.elte.graphalgorithms.core.GeneralGraphNode;

/**
 *
 * @author nagysan
 */
public class ColorableGraphNode extends GeneralGraphNode implements Comparable<ColorableGraphNode>{
  
    public enum Color {WHITE,GRAY,BLACK,RED,BLUE,GREEN,YELLOW,PURPLE};
    
    private Color color = Color.WHITE;
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public int compareTo(ColorableGraphNode o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColorableGraphNode other = (ColorableGraphNode) obj;
        if (!other.getId().equals(getId())) {
            return false;
        }
        return true;
    }
    
    
}
