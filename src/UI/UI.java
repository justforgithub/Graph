package UI;

import Data.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 * Created by Deviltech on 28.04.2016.
 */
public class UI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane drawPane = new Pane();

        Graph graph = new Graph();

        graph.generateGraphNode(200, 200);
        graph.generateGraphNode(400, 400);
        graph.generateGraphEdge(2, graph.graphNodes.get(0), graph.graphNodes.get(1));

        for(GraphEdge currentEdge: graph.graphEdges){
            drawPane.getChildren().add(currentEdge.getGroup());
        }
        for(GraphNode currentNode: graph.graphNodes){
            drawPane.getChildren().add(currentNode.getGroup());
        }



        drawPane.setOnMouseClicked((event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                graph.graphNodes.get(0).SetCoordinates(event.getX(), event.getY());
                graph.graphNodes.get(0).updateObject();
            }
            if (event.getButton().equals(MouseButton.SECONDARY)){
                graph.graphNodes.get(1).SetCoordinates(event.getX(), event.getY());
                graph.graphNodes.get(1).updateObject();
            }


        });



        Scene scene = new Scene(drawPane, 800, 800);
        primaryStage.setTitle("Graph Stuff");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
