package UI;

import Data.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Data.Values.PaneState;

import java.util.ArrayList;


/**
 * Created by Deviltech on 28.04.2016.
 */
public class UI extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {

        SelectionModel selectionModel = new SelectionModel();

        ToolBar buttonToolBar = new ToolBar();
        VBox mainBox = new VBox();

        Pane drawPane = new Pane();

        drawPane.setStyle("-fx-background-color: lightblue;");
        drawPane.setPrefSize(800, 750);

        Graph graph = new Graph();

        BooleanProperty isConversionNode = new SimpleBooleanProperty(true);


        drawPane.setOnMouseClicked((event) -> {
            // On leftclick
            if(event.getButton().equals(MouseButton.PRIMARY)){
                switch (graph.graphState){
                    // Idle: do nothing
                    case IDLE:
                        break;
                    // Node: generate Graph on mouse position
                    case GRAPHNODE:
                        if(!isConversionNode.getValue()) {
                            drawPane.getChildren().add(graph.generateStandardGraphNode(event.getX(), event.getY()).getGroup());
                        } else {
                            drawPane.getChildren().add(graph.generateConversionGraphNode(event.getX(), event.getY()).getGroup());
                        }
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
        MenuItem predefined2SubItem = new MenuItem("Conversion Example");

        // Checkmenuitems
        CheckMenuItem satCheckItem = new CheckMenuItem("allow invalid edge swaps [TODO]");

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
        fileMenu.getItems().addAll(clearItem, predefinedItem, loadItem, saveItem, exitItem);
        optionsMenu.getItems().addAll(satCheckItem);

        predefinedItem.getItems().addAll(predefined1SubItem, predefined2SubItem);

        // Togglebuttons
        ToggleButton cancelButton = new ToggleButton("Cancel [ESC]");
        ToggleButton nodeButton = new ToggleButton("Create Node [ALT]");
        ToggleButton edgeButton = new ToggleButton("Create Edge [CTRL]");
        ToggleButton selectButton = new ToggleButton("Select Elements");
        ToggleButton nodeTypeButton = new ToggleButton("Normal Node");
        ToggleButton weightButton = new ToggleButton("Edge weight: 1");

        Button deleteSelectedButton = new Button("delete selected Edge");


        isConversionNode.bind(nodeTypeButton.selectedProperty());
        weightButton.setStyle("-fx-background-color: red;");
        weightButton.setText(Values.weightButtonSelected);

        // buttonlist for unselection of other buttons on buttonclick
        ArrayList<ToggleButton> buttonList = new ArrayList<>();
        buttonList.add(cancelButton);
        buttonList.add(nodeButton);
        buttonList.add(edgeButton);
        buttonList.add(selectButton);

        cancelButton.setSelected(true);

        satCheckItem.setSelected(true);

        // TODO init
        // For titledPane for Mode Details
        Pane idlePane = new Pane();
        idlePane.getChildren().add(new Text("placeholder"));
        idlePane.setPrefHeight(100);

        Pane nodePane = new Pane();
        nodePane.getChildren().addAll(nodeTypeButton);
        nodePane.setPrefHeight(100);

        Pane edgePane = new Pane();
        edgePane.getChildren().addAll(weightButton);
        edgePane.setPrefHeight(100);

        Pane selectPane = new Pane();
        selectPane.getChildren().addAll(deleteSelectedButton);
        selectPane.setPrefHeight(100);

        TitledPane titledPane = new TitledPane("General Details", idlePane);
        titledPane.setExpanded(false);


        // Set State to IDLE
        java.util.function.Consumer makeIDLE = (value)->{
            graph.graphState = PaneState.IDLE;
            cancelButton.setSelected(true);
            unselectOtherButtons(cancelButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
            selectionModel.clear();
            titledPane.setContent(idlePane);
            titledPane.setText("General Details");
        };

        // Set State to GRAPHNODE
        java.util.function.Consumer makeGRAPHNODE = (value)->{
            graph.graphState = PaneState.GRAPHNODE;
            nodeButton.setSelected(true);
            unselectOtherButtons(nodeButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
            selectionModel.clear();
            titledPane.setContent(nodePane);
            titledPane.setText("Node Creation Details");
            titledPane.setExpanded(true);

        };

        // Set State to GRAPHEDGE
        java.util.function.Consumer makeGRAPHEDGE = (value)->{
            graph.graphState = PaneState.GRAPHEDGE;
            edgeButton.setSelected(true);
            unselectOtherButtons(edgeButton, buttonList);
            selectionModel.clear();
            titledPane.setContent(edgePane);
            titledPane.setText("Edge Creation Details");
            titledPane.setExpanded(true);

        };

        // Set State to SELECTED
        java.util.function.Consumer makeSELECTED = (value)->{
            graph.graphState = PaneState.SELECTED;
            selectButton.setSelected(true);
            unselectOtherButtons(selectButton, buttonList);
            titledPane.setContent(selectPane);
            titledPane.setText("Selection Details");
            titledPane.setExpanded(true);

        };


        nodeTypeButton.selectedProperty().addListener( event -> {
            if(nodeTypeButton.isSelected()){
                nodeTypeButton.setText("Conversion Node");
            } else {
                nodeTypeButton.setText("Normal Node");
            }
        });

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

        // activate Element Selection State
        selectButton.setOnAction(event -> {
            if(selectButton.isSelected()){
                makeSELECTED.accept(null);
            } else {
                makeIDLE.accept(null);
            }
        });


        //buttonToolBar.setPadding(new Insets(3, 5 ,3, 5));
        buttonToolBar.getItems().addAll(cancelButton, edgeButton, nodeButton, selectButton);

        mainBox.getChildren().addAll(menuBar, buttonToolBar, titledPane, drawPane);

        // Reset the view
        clearItem.setOnAction((event)->{
            graph.reset();
            drawPane.getChildren().clear();

        });

        // first Graph example
        predefined1SubItem.setOnAction(event -> {
            graph.reset();
            ExampleGraphs.example1(graph);
            fillPanewithGraphElements(drawPane, graph);
        });

        // second Graph example
        predefined2SubItem.setOnAction(event -> {
            graph.reset();
            ExampleGraphs.conversionExample(graph);
            fillPanewithGraphElements(drawPane, graph);
        });

        exitItem.setOnAction((event)->{
            Platform.exit();
        });


        Scene scene = new Scene(mainBox, 800, 800);

        // Add Selection Listener to Pane
        new RubberBandSelection(drawPane, selectionModel, graph);


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

        deleteSelectedButton.setOnAction((event) -> {
            graph.deleteSelectedElements(selectionModel, drawPane);
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
        for(AGraphNode currentNode: graph.graphNodes){
            pane.getChildren().add(currentNode.getGroup());
        }
    }

}
