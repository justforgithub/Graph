package Data;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Deviltech on 07.05.2016.
 */
public abstract class AGraphNode {


    public ArrayList<GraphEdge> graphEdges;
    public Graph graph;
    public double x;
    public double y;
    public StackPane pane;
    public Group group;


    // Constructor
    public AGraphNode(Graph graph, double x, double y) {
        this.graphEdges = new ArrayList<>();
        this.graph = graph;
        this.x = x;
        this.y = y;
        this.pane = new StackPane();
        this.group = new Group(drawObject());

    }



    /**
     * add GraphEdge to StandardGraphNode
     *
     * @param graphEdge
     */
    public void addEdge(GraphEdge graphEdge) {
        graphEdges.add(graphEdge);
    }

    /**
     * Remove GraphEdge from StandardGraphNode
     *
     * @param graphEdge
     */
    public void removeEdge(GraphEdge graphEdge) {
        for (Iterator<GraphEdge> iter = graphEdges.listIterator(); iter.hasNext(); ) {
            GraphEdge currentGraphEdge = iter.next();
            if (currentGraphEdge.equals(graphEdge)) {
                iter.remove();
                updateObject();
            }
        }
    }

    public Group getGroup() {
        return group;
    }

    public void SetCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public StackPane getPane() {
        return pane;
    }

    /**
     * sums up the weights of incoming graphEdges
     *
     * @return weights
     */
    public int getIncomingWeights() {
        double weight = 0.0;
        for (GraphEdge currentGraphEdge : graphEdges) {
            if (this.equals(currentGraphEdge.getDirectionGraphNode())) {
                // Workaround for double scoring of circle edge
                if (currentGraphEdge.getDirectionGraphNode().equals(currentGraphEdge.getOriginGraphNode())) {
                    weight += currentGraphEdge.getWeight() * 0.5;
                } else {
                    weight += currentGraphEdge.getWeight();
                }
            }
        }
        return (int) weight;
    }


    /**
     * Drawing function for Circle
     *
     * @return labeled circle
     */
    private Group drawObject() {

        group = new Group();

        double radius = Values.nodeRadius;

        Rectangle rectangle = new Rectangle(radius, radius);

        int weights = getIncomingWeights();
        // if satisfied, draw different fill color
        rectangle.setFill(isSatisfied()? Values.circleFill : Values.circleFillunsat);
        rectangle.setStroke(Values.circleStroke);

        // Label circle with weight of the StandardGraphNode
        Text text = new Text(Integer.toString(weights));

        pane.getChildren().addAll(rectangle, text);
        pane.setTranslateX(x - radius);
        pane.setTranslateY(y - radius);
        pane.setOnMousePressed((event) -> {
            if (graph.graphState == Values.PaneState.GRAPHEDGE) {
                if (graph.firstNodeSelection == null) {
                    System.out.println("first selected");
                    graph.firstNodeSelection = this;
                } else {
                    if (graph.isEdgeExistent(graph.firstNodeSelection, this)) {
                        System.out.println("Edge existant");
                    } else {
                        graph.secondNodeSelection = this;
                    }
                }
            }
        });



        Group backgroundGroup = generateBackgroundShape(x-radius*0.5, y-radius*0.5, radius);
        group.getChildren().addAll(backgroundGroup.getChildren());
        System.out.println(backgroundGroup.getChildren().size());
        group.getChildren().add(pane);
        pane.toFront();

        return group;
    }

    /**
     * Redraws Shape
     */
    public void updateObject() {
        group.getChildren().clear();
        updateEdges();
        group.getChildren().add(drawObject());

    }

    /**
     * helper for updateObject
     */
    private void updateEdges() {
        for (GraphEdge currentEdge : graphEdges) {
            currentEdge.drawObject();
            // Place Edge behind Node
            currentEdge.getGroup().toBack();
        }
    }




    /**
     * Checks if graph Node is satisfied
     * @return
     */
    public abstract boolean isSatisfied();

    /**
     * generate Background Symbol for drawn Node
     * @param x
     * @param y
     * @param radius
     */
    public abstract Group generateBackgroundShape(double x, double y, double radius);


}
