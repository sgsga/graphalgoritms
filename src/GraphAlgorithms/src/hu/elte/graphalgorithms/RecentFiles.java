package hu.elte.graphalgorithms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Balassa Imre
 */
public class RecentFiles {
    private static final int MAX_COUNT = 10;
    private List<String> recent = new LinkedList<>();
    
    public void addRecent(String file) {
        recent.remove(file);
        recent.add(0, file);
    }
    
    public List<String> getRecentFiles() {
        return Collections.unmodifiableList(recent);
    }
    
    public void loadFromProperties(Properties prop) {
        if (prop == null) {
            return;
        }
        recent = new LinkedList<>();
        for (int i=MAX_COUNT-1; i>= 0; i--) {
                String recentFilename = prop.getProperty("recent_" + i, null);
                if (recent != null) {
                    addRecent(recentFilename);
                }
         }   
    }
    
    public void saveToProperties(Properties prop) {
        for (int i=0; i< MAX_COUNT; i++) {
            if (i > recent.size() -1) {
                prop.remove("recent_" + i);
            } else {
                prop.setProperty("recent_" + i, recent.get(i));
            }
        }
    }
}
 