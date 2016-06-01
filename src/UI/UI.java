package UI;

import Data.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Data.Values.PaneState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by Deviltech on 28.04.2016.
 */
public class UI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        final double exampleScale = Values.standardScale1;

        SelectionModel selectionModel = new SelectionModel();

        ToolBar buttonToolBar = new ToolBar();
        ToolBar nodeToolBar = new ToolBar();
        ToolBar edgeToolBar = new ToolBar();
        ToolBar selectionToolBar = new ToolBar();

        VBox mainBox = new VBox();

        Pane drawPane = new Pane();

        drawPane.setStyle("-fx-background-color: lightblue;");
        drawPane.setPrefSize(Values.paneWidth, Values.paneHeigth);

        Graph graph = new Graph(drawPane);




        // Bar and Menus
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu optionsMenu = new Menu("Options");

        // Menuitems
        MenuItem clearItem = new MenuItem("Clear");
        Menu predefinedItem = new Menu("Predefined Graphs");
        MenuItem loadItem = new MenuItem("Load Graph from File");
        MenuItem saveItem = new MenuItem("Save Graph to File");
        MenuItem exitItem = new MenuItem("Exit");

        // Submenuitems
        MenuItem predefinedExampleSubItem = new MenuItem("Example");
        MenuItem predefinedConvSubItem = new MenuItem("Conversion");
        MenuItem predefinedLatchSubItem = new MenuItem("Latch");
        MenuItem predefinedCrossSubItem = new MenuItem("Crossover");
        MenuItem predefinedTermSubItem = new MenuItem("Terminator");
        MenuItem predefinedHalfCSubItem = new MenuItem("Half Crossover");
        MenuItem predefinedProtOrSubItem = new MenuItem("Protected OR");



        // Checkmenuitems
        CheckMenuItem satCheckItem = new CheckMenuItem("allow invalid edge swaps");
        CheckMenuItem isSmallScale = new CheckMenuItem("smaller Example scale");

        menuBar.getMenus().addAll(fileMenu, optionsMenu);
        fileMenu.getItems().addAll(clearItem, predefinedItem, loadItem, saveItem, exitItem);
        optionsMenu.getItems().addAll(satCheckItem, isSmallScale);

        predefinedItem.getItems().addAll(predefinedExampleSubItem, predefinedConvSubItem, predefinedLatchSubItem,
                predefinedCrossSubItem, predefinedTermSubItem, predefinedHalfCSubItem, predefinedProtOrSubItem);

        // Togglebuttons
        ToggleButton cancelButton = new ToggleButton("Cancel [ESC]");
        ToggleButton nodeButton = new ToggleButton("Create Node [ALT]");
        ToggleButton edgeButton = new ToggleButton("Create Edge [CTRL]");
        ToggleButton selectButton = new ToggleButton("Select Elements");
        ToggleButton weightButton = new ToggleButton("Edge weight: 1");

        ToggleButton standardNodeButton = new ToggleButton("Standard");
        ToggleButton conversionNodeButton = new ToggleButton("Conversion");
        ToggleButton inputNodeButton = new ToggleButton("Input");
        ToggleButton outputNodeButton = new ToggleButton("Output");

        Button cancelEdgeButton = new Button("Cancel Edge");
        Button deleteSelectedButton = new Button("delete selected Elements");

        Rectangle weightPreview = new Rectangle(20, 20, Values.Weight1Color);

        // Change Color of rectangle based on current edge weight
        graph.graphWeigth.addListener(event -> {
            if (graph.graphWeigth.getValue().equals(Values.standardWeight1)){
                weightPreview.setFill(Values.Weight1Color);
            } else {
                weightPreview.setFill(Values.Weight2Color);
            }
        });

        // Set isInvalidSwapAllowed based on check item
        satCheckItem.setOnAction(event -> {
            graph.isInvalidEdgeSwapAllowed.set(satCheckItem.isSelected());
            if(satCheckItem.isSelected()){
                // if this mode is allowed, no exercise can be finished
                graph.isUnicornPossible = false;
            }
        });

        // Set scale of graph examples (for smaller screens)
        isSmallScale.setOnAction(event -> {
            if(isSmallScale.selectedProperty().getValue()){
                graph.exampleScale=Values.standardScale2;
            } else {
                graph.exampleScale=Values.standardScale1;
            }
        });


        // buttonlist for unselection of other buttons on buttonclick
        ArrayList<ToggleButton> buttonList = new ArrayList<>();
        buttonList.add(cancelButton);
        buttonList.add(nodeButton);
        buttonList.add(edgeButton);
        buttonList.add(selectButton);

        ArrayList<ToggleButton> nodeButtonList = new ArrayList<>();
        nodeButtonList.add(standardNodeButton);
        nodeButtonList.add(conversionNodeButton);
        nodeButtonList.add(inputNodeButton);
        nodeButtonList.add(outputNodeButton);

        // Misc setting stuff

        cancelButton.setSelected(true);
        standardNodeButton.setSelected(true);
        standardNodeButton.setStyle("-fx-font-weight: bold");

        cancelEdgeButton.setDisable(true);
        weightButton.setText(Values.weightButtonSelected);

        satCheckItem.setSelected(Values.isInvalidEdgeSwapAllowed);

        // For titledPane for Mode Details
        Pane idlePane = new Pane();
        idlePane.getChildren().add(new VBox(graph.generalText));
        idlePane.setPrefHeight(100);

        Pane nodePane = new Pane();
        nodePane.getChildren().addAll(nodeToolBar);
        nodePane.setPrefHeight(100);

        Pane edgePane = new Pane();
        edgePane.getChildren().addAll(edgeToolBar);
        edgePane.setPrefHeight(100);

        Pane selectPane = new Pane();
        selectPane.getChildren().addAll(selectionToolBar);
        selectPane.setPrefHeight(100);

        TitledPane titledPane = new TitledPane("General Details", idlePane);
        titledPane.setExpanded(false);



        // Set State to IDLE
        java.util.function.Consumer makeIDLE = (value) -> {
            graph.graphState = PaneState.IDLE;
            cancelButton.setSelected(true);
            unselectOtherButtons(cancelButton, buttonList);
            graph.firstNodeSelection = null;
            graph.secondNodeSelection = null;
            selectionModel.clear();
            titledPane.setContent(idlePane);
            titledPane.setText("General Details");
            cancelEdgeButton.setDisable(true);
            titledPane.setExpanded(false);
        };

        // Set State to GRAPHNODE
        java.util.function.Consumer makeGRAPHNODE = (value) -> {
            titledPane.setExpanded(false);
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
        java.util.function.Consumer makeGRAPHEDGE = (value) -> {
            graph.graphState = PaneState.GRAPHEDGE;
            edgeButton.setSelected(true);
            unselectOtherButtons(edgeButton, buttonList);
            selectionModel.clear();
            titledPane.setContent(edgePane);
            titledPane.setText("Edge Creation Details");
            titledPane.setExpanded(true);

        };

        // Set State to SELECTED
        java.util.function.Consumer makeSELECTED = (value) -> {
            graph.graphState = PaneState.SELECTED;
            selectButton.setSelected(true);
            unselectOtherButtons(selectButton, buttonList);
            titledPane.setContent(selectPane);
            titledPane.setText("Selection Details");
            titledPane.setExpanded(true);

        };

        // Set node Type to Standard
        standardNodeButton.setOnAction(event -> {
            graph.nodeState = Values.NodeState.STANDARD;
            standardNodeButton.setSelected(true);
            unselectOtherButtons(standardNodeButton, nodeButtonList);
        });

        // Set node Type to Conversion
        conversionNodeButton.setOnAction(event -> {
            graph.nodeState = Values.NodeState.CONVERSION;
            conversionNodeButton.setSelected(true);
            unselectOtherButtons(conversionNodeButton, nodeButtonList);
        });

        // Set node Type to Input
        inputNodeButton.setOnAction(event -> {
            graph.nodeState = Values.NodeState.INPUT;
            inputNodeButton.setSelected(true);
            unselectOtherButtons(inputNodeButton, nodeButtonList);
        });

        // Set node Type to Output
        outputNodeButton.setOnAction(event -> {
            graph.nodeState = Values.NodeState.OUTPUT;
            outputNodeButton.setSelected(true);
            unselectOtherButtons(outputNodeButton, nodeButtonList);
        });

        // toggle between weights
        weightButton.selectedProperty().addListener(event -> {
            if (!weightButton.isSelected()) {
                weightButton.setText(Values.weightButtonSelected);
                graph.graphWeigth.set(Values.standardWeight1);
            } else {
                weightButton.setText(Values.weightButtonUnselected);
                graph.graphWeigth.set(Values.standardWeight2);
            }
        });

        // Cancel first edge selection
        cancelEdgeButton.setOnAction(event -> {
            graph.firstNodeSelection = null;
            cancelEdgeButton.setDisable(true);
        });

        // activate IDLE state
        cancelButton.setOnAction(event -> {
            if (cancelButton.isSelected()) {
                makeIDLE.accept(null);
            }
        });

        // activate Node generation State
        nodeButton.setOnAction(event -> {
            if (nodeButton.isSelected()) {
                makeGRAPHNODE.accept(null);
            } else {
                makeIDLE.accept(null);
            }
        });

        // activate Edge generation State
        edgeButton.setOnAction(event -> {
            if (edgeButton.isSelected()) {
                makeGRAPHEDGE.accept(null);
            } else {
                makeIDLE.accept(null);
            }
        });

        // activate Element Selection State
        selectButton.setOnAction(event -> {
            if (selectButton.isSelected()) {
                makeSELECTED.accept(null);
            } else {
                makeIDLE.accept(null);
            }
        });


        //buttonToolBar.setPadding(new Insets(3, 5 ,3, 5));
        buttonToolBar.getItems().addAll(cancelButton, edgeButton, nodeButton, selectButton);
        nodeToolBar.getItems().addAll(standardNodeButton, conversionNodeButton, inputNodeButton, outputNodeButton);
        edgeToolBar.getItems().addAll(weightButton, weightPreview, cancelEdgeButton);
        selectionToolBar.getItems().addAll(deleteSelectedButton);


        mainBox.getChildren().addAll(menuBar, buttonToolBar, titledPane, drawPane);

        // Set pane click events
        drawPane.setOnMouseClicked((event) -> {
            // On leftclick
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                switch (graph.graphState) {
                    // Idle: do nothing
                    case IDLE:
                        break;

                    // Node: generate Graph on mouse position
                    case GRAPHNODE:
                        Group g = graph.generateGraphNodeByState(event.getX(), event.getY(), graph.nodeState).getGroup();
                        drawPane.getChildren().add(g);
                        break;

                    // Edge: generate edge between 2 nodes, if possible
                    case GRAPHEDGE:
                        if (graph.secondNodeSelection != null) {
                            // Enable cancel button for edge cancel
                            cancelEdgeButton.setDisable(true);
                            drawPane.getChildren().add(graph.generateGraphEdge(graph.graphWeigth.getValue(), graph.firstNodeSelection, graph.secondNodeSelection).getGroup());
                            graph.firstNodeSelection.updateObject();
                            graph.secondNodeSelection.updateObject();
                            graph.firstNodeSelection = null;
                            graph.secondNodeSelection = null;
                        } else {
                            // Disable cancel button, no cancel possible
                            cancelEdgeButton.setDisable(false);
                        }
                        break;

                    default:
                        break;
                }
            }
        });

        // MENU ITEMS

        // Reset the view
        clearItem.setOnAction((event) -> {
            graph.reset();
            drawPane.getChildren().clear();

        });

        // Load Files //
        loadItem.setOnAction(event -> {
            // Prepare fielChooser
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a .graph file");

            // Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Graph files (*.graph)", "*.graph");
            fileChooser.getExtensionFilters().add(extFilter);

            //Open Dialog
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    // Parse
                    Parser.readInFile(file, graph, drawPane);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        saveItem.setOnAction(event -> {
            // Prepare fielChooser
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save a .graph file");

            // Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Graph files (*.graph)", "*.graph");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(primaryStage);
            Parser.writeToFile(file, graph);

        });

        // first Graph example
        predefinedExampleSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.example1(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // second Graph example
        predefinedConvSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.conversionExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // third Graph example
        predefinedLatchSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.LatchExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // fourth Graph example
        predefinedCrossSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.CrossoverExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // fifth Graph example
        predefinedTermSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.terminatorExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // sixth Graph example
        predefinedHalfCSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.HalfCrossoverExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // seventh Graph example
        predefinedProtOrSubItem.setOnAction(event -> {
            graph.reset();
            drawPane.getChildren().clear();
            ExampleGraphs.ProtectedOrExample(graph, drawPane);
            fillPanewithGraphElements(drawPane, graph);
        });

        // Close Application
        exitItem.setOnAction((event) -> {
            Platform.exit();
        });



        Scene scene = new Scene(mainBox, Values.paneWidth, Values.paneHeigth + 50);



        // Add Selection Listener to Pane
        new RubberBandSelection(drawPane, selectionModel, graph);


        scene.setOnKeyPressed((event) -> {
            switch (event.getCode()) {
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
                default:
                    break;
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {

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
    private void unselectOtherButtons(ToggleButton button, ArrayList<ToggleButton> otherButtons) {
        for (ToggleButton currentButton : otherButtons) {
            if (!button.equals(currentButton)) {
                currentButton.setSelected(false);
            }
        }
        button.requestFocus();
    }

    /**
     * fill pane with edges and nodes of the graph (overwrites existing nodes)
     *
     * @param pane
     * @param graph
     */
    private void fillPanewithGraphElements(Pane pane, Graph graph) {
        // fill DrawPane with edges and Nodes of the graph
        for (GraphEdge currentEdge : graph.graphEdges) {
            pane.getChildren().add(currentEdge.getGroup());
        }
        for (AGraphNode currentNode : graph.graphNodes) {
            pane.getChildren().add(currentNode.getGroup());
        }


    }

}
