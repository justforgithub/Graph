package UI;

import Data.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * Created by Deviltech on 28.04.2016.
 */
public class UI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox buttonBox = new HBox();
        VBox mainBox = new VBox();

        Pane drawPane = new Pane();
        drawPane.setStyle("-fx-background-color: lightblue;");
        drawPane.setPrefSize(800, 750);

        Graph graph = new Graph();

        graph.generateGraphNode(200, 200);
        graph.generateGraphNode(400, 400);
        graph.generateGraphNode(300, 600);
        graph.generateGraphNode(500, 300);
        graph.generateGraphEdge(2, graph.graphNodes.get(0), graph.graphNodes.get(1));
        graph.generateGraphEdge(1, graph.graphNodes.get(1), graph.graphNodes.get(2));
        graph.generateGraphEdge(1, graph.graphNodes.get(3), graph.graphNodes.get(1));

        // fill DrawPane with edges and Nodes of the graph
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

        Button weightButton = new Button("Toggle weight");
        Button directionButton = new Button("Toggle direction");

        weightButton.setOnAction((event)->{
            graph.graphEdges.forEach((currentEdge)->{
                currentEdge.toggleWeight();
            });
        });

        directionButton.setOnAction((event)-> {
            graph.graphEdges.forEach((currentEdge)->{
                currentEdge.swapEdgeDirection();
            });
        });

        buttonBox.getChildren().addAll(weightButton, directionButton);

        mainBox.getChildren().addAll(buttonBox, drawPane);

        Scene scene = new Scene(mainBox, 800, 800);
        primaryStage.setTitle("Graph Stuff");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
