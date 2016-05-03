package UI;

import Data.*;
import javafx.application.Application;
import javafx.application.Platform;
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



        drawPane.setOnMouseClicked((event) -> {
            // On leftclick
            if(event.getButton().equals(MouseButton.PRIMARY)){
                switch (graph.graphState){
                    // Idle: do nothing
                    case IDLE:
                        break;
                    // Node: generate Graph on mouse position
                    case GRAPHNODE:
                        drawPane.getChildren().add(graph.generateGraphNode(event.getX(), event.getY()).getGroup());
                        break;
                    // Edge: generate edge between 2 nodes, if possible
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
            }

        });

        // Bar and Menus
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu optionsMenu = new Menu("Options");

        // Menuitems
        MenuItem clearItem = new MenuItem("Clear");
        Menu predefinedItem = new Menu("Predefined Graphs");
        MenuItem loadItem = new MenuItem("Load Graph [TODO]");
        MenuItem saveItem = new MenuItem("Save Graph [TODO]");
        MenuItem exitItem = new MenuItem("Exit");

        // Submenuitems
        MenuItem predefined1SubItem = new MenuItem("Simple Graph");

        // Checkmenuitems
        CheckMenuItem satCheckItem = new CheckMenuItem("allow invalid edge swaps [TODO]");

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
        fileMenu.getItems().addAll(clearItem, predefinedItem, loadItem, saveItem, exitItem);
        optionsMenu.getItems().addAll(satCheckItem);

        predefinedItem.getItems().addAll(predefined1SubItem);

        // Togglebuttons
        ToggleButton cancelButton = new ToggleButton("Cancel [ESC]");
        ToggleButton nodeButton = new ToggleButton("Create Node [ALT]");
        ToggleButton edgeButton = new ToggleButton("Create Edge [CTRL]");
        ToggleButton weightButton = new ToggleButton("Edge weight: 1");

        weightButton.setStyle("-fx-background-color: red;");
        weightButton.setText(Values.weightButtonSelected);

        // buttonlist for unselection of other buttons on buttonclick
        ArrayList<ToggleButton> buttonList = new ArrayList<>();
        buttonList.add(cancelButton);
        buttonList.add(nodeButton);
        buttonList.add(edgeButton);

        cancelButton.setSelected(true);

        satCheckItem.setSelected(true);


        // Set State to IDLE
        java.util.function.Consumer makeIDLE = (value)->{
            graph.graphState = PaneState.IDLE;
            cancelButton.setSelected(true);
            unselectOtherButtons(cancelButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
        };

        // Set State to GRAPHNODE
        java.util.function.Consumer makeGRAPHNODE = (value)->{
            graph.graphState = PaneState.GRAPHNODE;
            nodeButton.setSelected(true);
            unselectOtherButtons(nodeButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
        };

        // Set State to GRAPHEDGE
        java.util.function.Consumer makeGRAPHEDGE = (value)->{
            graph.graphState = PaneState.GRAPHEDGE;
            edgeButton.setSelected(true);
            unselectOtherButtons(edgeButton, buttonList);
        };


        // toggle between weights
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

        // activate IDLE state
        cancelButton.setOnAction(event -> {
            if (cancelButton.isSelected()) {
                makeIDLE.accept(null);
            }
        });

        // activate Node generation State
        nodeButton.setOnAction(event -> {
            if(nodeButton.isSelected()){
               makeGRAPHNODE.accept(null);
            } else {
               makeIDLE.accept(null);
            }
        });

        // activate Edge generation State
        edgeButton.setOnAction(event -> {
            if(edgeButton.isSelected()){
                makeGRAPHEDGE.accept(null);
            } else {
               makeIDLE.accept(null);
            }
        });


        buttonBox.getChildren().addAll(cancelButton, edgeButton,nodeButton,  new Separator(), weightButton);

        mainBox.getChildren().addAll(menuBar, buttonBox, drawPane);

        // Reset the view
        clearItem.setOnAction((event)->{
            graph.reset();
            drawPane.getChildren().clear();

        });

        // first Graph example
        predefined1SubItem.setOnAction(event -> {
            ExampleGraphs.example1(graph);
            fillPanewithGraphElements(drawPane, graph);
        });

        exitItem.setOnAction((event)->{
            Platform.exit();
        });



        Scene scene = new Scene(mainBox, 800, 800);

        scene.setOnKeyPressed((event)-> {
            switch (event.getCode()){
                case ESCAPE:
                    // IDLE
                    makeIDLE.accept(null);
                    break;
                case ALT:
                    // GRAPHNODE
                    makeGRAPHNODE.accept(null);
                    break;
                case CONTROL:
                    // GRAPHEDGE
                    makeGRAPHEDGE.accept(null);
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

    /**
     * fill pane with edges and nodes of the graph (overwrites existing nodes)
     * @param pane
     * @param graph
     */
    private void fillPanewithGraphElements(Pane pane, Graph graph){
        // fill DrawPane with edges and Nodes of the graph
        pane.getChildren().clear();
        for(GraphEdge currentEdge: graph.graphEdges){
            pane.getChildren().add(currentEdge.getGroup());
        }
        for(GraphNode currentNode: graph.graphNodes){
            pane.getChildren().add(currentNode.getGroup());
        }
    }

}
