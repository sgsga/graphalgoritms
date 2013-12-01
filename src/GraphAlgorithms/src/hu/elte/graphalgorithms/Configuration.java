
package hu.elte.graphalgorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Balasas Imre
 */
public class Configuration {
     
    private static final String USER_CONFIGRATION_FILENAME = ".eltegraphrc";
    private final RecentFiles recentFiles;
    
    public RecentFiles getRecentFiles() {
        return recentFiles;
    }
    
    public Configuration(RecentFiles rf) {
        recentFiles = rf;
    }
      
    public void loadConfiguration(){
        Properties userProperties = new Properties();
        File configFile = new File(System.getProperty("user.home"), USER_CONFIGRATION_FILENAME);
        if (configFile.exists()) {
            try {
                userProperties.load(new FileInputStream(configFile));
                recentFiles.loadFromProperties(userProperties);  
            } catch (IOException ex) {
                Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void saveConfigration() {
        Properties userProperties = new Properties();
        try {
            recentFiles.saveToProperties(userProperties);
            File configFile = new File(System.getProperty("user.home"), USER_CONFIGRATION_FILENAME);
            userProperties.store(new FileOutputStream(configFile), "Graphalgorithms configuration file.");   
        } catch (IOException ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
