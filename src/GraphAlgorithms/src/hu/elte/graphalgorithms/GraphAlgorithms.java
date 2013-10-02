/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

/**
 *
 * @author nagysan
 */
public class GraphAlgorithms extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
         Group root = new Group();
    Scene scene = new Scene(root, 260, 80);
    stage.setScene(scene);

    Group g = new Group();

    Polyline polyline = new Polyline();
    polyline.getPoints().addAll(new Double[]{
        100.0, 100.0,
        110.0, 110.0,
        100.0, 100.0,
        110.0, 90.0,
        100.0, 100.0,
        200.0, 100.0});
    
    g.getChildren().add(polyline);
    
    scene.setRoot(g);
    stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}