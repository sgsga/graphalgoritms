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
public class ColorableGraphNode extends GeneralGraphNode {
  
    public enum Color {WHITE,GRAY,BLACK,RED,BLUE,GREEN,YELLOW,PURPLE};
    
    private Color color = Color.WHITE;
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
}
