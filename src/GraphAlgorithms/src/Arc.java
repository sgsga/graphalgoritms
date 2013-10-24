import hu.elte.graphalgorithms.algorithms.implementations.KruskalAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.ExtendedDirectedGraph;
import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Arc extends Application {

  @Override
  public void start(Stage stage) throws IdAlreadySetException, ArcAlreadyExistsException, UndirectedGraphRequiredException {
    ExtendedDirectedGraph<ColorableGraphNode,ColorableGraphArc> graph = new ExtendedDirectedGraph<>();
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createNode(new ColorableGraphNode());
    graph.createArc(0, 1, 0.0f, new ColorableGraphArc());
    graph.createArc(1, 0, 0.0f, new ColorableGraphArc());
    graph.createArc(1, 2, 12.0f, new ColorableGraphArc());
    graph.createArc(2, 1, 12.0f, new ColorableGraphArc());
    graph.createArc(2, 3, 15.0f, new ColorableGraphArc());
    graph.createArc(3, 2, 15.0f, new ColorableGraphArc());
    graph.createArc(3, 4, 17.0f, new ColorableGraphArc());
    graph.createArc(4, 3, 17.0f, new ColorableGraphArc());
    graph.createArc(6, 7, 17.0f, new ColorableGraphArc());
    graph.createArc(7, 6, 17.0f, new ColorableGraphArc());
    
    graph.createArc(5, 7, 17.0f, new ColorableGraphArc());
    graph.createArc(7, 5, 17.0f, new ColorableGraphArc());
    graph.createArc(5, 6, 17.0f, new ColorableGraphArc());
    graph.createArc(6, 5, 17.0f, new ColorableGraphArc());
    
    for(ColorableGraphArc ga : graph.getArcs()){
        System.out.println(ga);
    }
    System.out.println(graph.toJSON());
    graph.fromJSON(graph.toJSON(), null);
    //graph.createArc(7, 1, 18.0f, new ColorableGraphArc());
    KruskalAlgorithm ka = new KruskalAlgorithm();
    ka.initialize(graph);
    System.out.println(ka.run());
        for(ColorableGraphArc ga : graph.getArcs()){
        System.out.println(ga);
    }
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