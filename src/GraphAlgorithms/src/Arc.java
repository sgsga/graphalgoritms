import hu.elte.graphalgorithms.algorithms.implementations.KruskalAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.DirectedGraph;
import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Arc extends Application {

  @Override
  public void start(Stage stage) throws IdAlreadySetException, ArcAlreadyExistsException {
    DirectedGraph<ColorableGraphNode,ColorableGraphArc> graph = new DirectedGraph<>();
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createArc(0, 1, 0.0f, new ColorableGraphArc());
    graph.createArc(1, 2, 21.0f, new ColorableGraphArc());
    graph.createArc(2, 3, 12.56f, new ColorableGraphArc());
    graph.createArc(3, 4, 13.0f, new ColorableGraphArc());
    graph.createArc(4, 5, 34.34f, new ColorableGraphArc());
    graph.createArc(5, 6, 15.0f, new ColorableGraphArc());
    graph.createArc(6, 7, 16.4f, new ColorableGraphArc());
    graph.createArc(3, 2, 17.0f, new ColorableGraphArc());
    graph.createArc(7, 1, 18.0f, new ColorableGraphArc());
      KruskalAlgorithm ka = new KruskalAlgorithm();
      ka.initialize(graph);
      ka.run();
    Group root = new Group();
    Scene scene = new Scene(root, 260, 80);
    stage.setScene(scene);

    Group g = new Group();

    Polyline polyline = new Polyline();
    polyline.getPoints().addAll(new Double[]{
        0.0, 0.0,
        20.0, 10.0,
        10.0, 20.0 });
    
    g.getChildren().add(polyline);
    
    scene.setRoot(g);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}