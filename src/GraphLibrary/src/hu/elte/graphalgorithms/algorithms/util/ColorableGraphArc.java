/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.util;

import hu.elte.graphalgorithms.core.GeneralGraphArc;

/**
 *
 * @author nagysan
 */
public class ColorableGraphArc extends GeneralGraphArc {
    public enum Color {WHITE,GRAY,BLACK,RED,BLUE,GREEN,YELLOW,PURPLE};
    private Color color = Color.BLACK;
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    @Override
    public String toString() {
        return "ColorableGraphArc(" + "id=" + getId() +",color="+getColor().toString()+")|"+ getFromId() + " == ("+getCost()+") ==> " + getToId();
    }
    
}
