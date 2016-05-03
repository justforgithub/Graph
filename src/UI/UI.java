package UI;

import Data.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Data.Values.PaneState;

import java.util.ArrayList;


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
        graph.generateGraphEdge(1, graph.graphNodes.get(1), graph.graphNodes.get(1));

        // fill DrawPane with edges and Nodes of the graph
        for(GraphEdge currentEdge: graph.graphEdges){
            drawPane.getChildren().add(currentEdge.getGroup());
        }
        for(GraphNode currentNode: graph.graphNodes){
            drawPane.getChildren().add(currentNode.getGroup());
        }




        drawPane.setOnMouseClicked((event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                switch (graph.graphState){
                    case IDLE:
                        break;
                    case GRAPHNODE:
                        drawPane.getChildren().add(graph.generateGraphNode(event.getX(), event.getY()).getGroup());
                        break;
                    case GRAPHEDGE:
                       if(graph.secondNodeSelection != null){
                           drawPane.getChildren().add(graph.generateGraphEdge(graph.graphWeigth, graph.firstNodeSelection, graph.secondNodeSelection).getGroup());
                           graph.firstNodeSelection.updateObject();
                           graph.secondNodeSelection.updateObject();
                           graph.firstNodeSelection = null;
                           graph.secondNodeSelection = null;
                       }
                        break;
                    default:
                        break;
                }
                //graph.graphNodes.get(1).SetCoordinates(event.getX(), event.getY());
                //graph.graphNodes.get(1).updateObject();
            }

        });

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu optionsMenu = new Menu("Options");

        MenuItem clearItem = new MenuItem("Clear");
        MenuItem exitItem = new MenuItem("Exit");

        CheckMenuItem satCheckItem = new CheckMenuItem("allow invalid edge swaps");

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
        fileMenu.getItems().addAll(clearItem, exitItem);
        optionsMenu.getItems().addAll(satCheckItem);

        ToggleButton cancelButton = new ToggleButton("Cancel [ESC]");
        ToggleButton nodeButton = new ToggleButton("Create Node [ALT]");
        ToggleButton edgeButton = new ToggleButton("Create Edge [CTRL]");
        ToggleButton weightButton = new ToggleButton("Edge weight: 1");

        weightButton.setStyle("-fx-background-color: red;");
        weightButton.setText(Values.weightButtonSelected);

        ArrayList<ToggleButton> buttonList = new ArrayList<>();
        buttonList.add(cancelButton);
        buttonList.add(nodeButton);
        buttonList.add(edgeButton);

        cancelButton.setSelected(true);

        weightButton.selectedProperty().addListener(event ->{
            if(!weightButton.isSelected()){
                weightButton.setStyle("-fx-background-color: red;");
                weightButton.setText(Values.weightButtonSelected);
                graph.graphWeigth = 1;
            } else {
                weightButton.setText(Values.weightButtonUnselected);
                weightButton.setStyle("-fx-background-color: lightblue;");
                graph.graphWeigth = 2;
            }
        });

        nodeButton.setOnAction(event -> {
            if(nodeButton.isSelected()){
                graph.graphState = PaneState.GRAPHNODE;
                unselectOtherButtons(nodeButton, buttonList);
            } else {
                graph.graphState = PaneState.IDLE;
                cancelButton.setSelected(true);
            }
        });

        edgeButton.setOnAction(event -> {
            if(edgeButton.isSelected()){
                graph.graphState = PaneState.GRAPHEDGE;
                unselectOtherButtons(edgeButton, buttonList);
            } else {
                graph.graphState = PaneState.IDLE;
                cancelButton.setSelected(true);
            }
        });


        buttonBox.getChildren().addAll(cancelButton, nodeButton, edgeButton, new Separator(), weightButton);

        mainBox.getChildren().addAll(menuBar, buttonBox, drawPane);

        // Reset the view
        clearItem.setOnAction((event)->{
            graph.reset();
            drawPane.getChildren().clear();

        });

        exitItem.setOnAction((event)->{
            Platform.exit();
        });

        // Set State to IDLE
        java.util.function.Consumer makeIDLE = (value)->{
            graph.graphState = PaneState.IDLE;
            cancelButton.setSelected(true);
            unselectOtherButtons(cancelButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
        };

        Scene scene = new Scene(mainBox, 800, 800);

        scene.setOnKeyPressed((event)-> {
            switch (event.getCode()){
                case ESCAPE:
                    makeIDLE.accept(null);
                    break;
                case ALT:
                    graph.graphState = PaneState.GRAPHNODE;
                    nodeButton.setSelected(true);
                    unselectOtherButtons(nodeButton, buttonList);
                    break;
                case CONTROL:
                    graph.graphState = PaneState.GRAPHEDGE;
                    edgeButton.setSelected(true);
                    unselectOtherButtons(edgeButton, buttonList);
                default: break;
            }
            if(event.getCode().equals(KeyCode.ESCAPE)){

            }

        });

        primaryStage.setTitle("Graph Stuff");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Iterate over buttons and unselect the others
     */
    private void unselectOtherButtons(ToggleButton button,ArrayList<ToggleButton> otherButtons){
        for(ToggleButton currentButton:  otherButtons){
            if(!button.equals(currentButton)){
                currentButton.setSelected(false);
            }
        }
    }

}
