package Data;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class GraphNode {

    private ArrayList<GraphEdge> graphEdges;
    private Graph graph;
    private double x;
    private double y;
    private StackPane pane;
    private Group group;


    // Constructor
    public GraphNode(Graph graph, double x, double y) {
        this.graphEdges = new ArrayList<>();
        this.graph = graph;
        this.x = x;
        this.y = y;
        this.pane = new StackPane();
        this.group = new Group(drawObject());

    }

    /**
     * add GraphEdge to GraphNode
     *
     * @param graphEdge
     */
    public void addEdge(GraphEdge graphEdge) {
        graphEdges.add(graphEdge);
    }

    /**
     * Remove GraphEdge from GraphNode
     *
     * @param graphEdge
     */
    public void removeEdge(GraphEdge graphEdge) {
        for (Iterator<GraphEdge> iter = graphEdges.listIterator(); iter.hasNext(); ) {
            GraphEdge currentGraphEdge = iter.next();
            if (currentGraphEdge.equals(graphEdge)) {
                iter.remove();
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
    private StackPane drawObject() {

        Rectangle rectangle = new Rectangle(Values.nodeRadius, Values.nodeRadius);

        int weights = getIncomingWeights();
        rectangle.setFill(weights >= 2 ? Values.circleFill : Values.circleFillunsat);
        rectangle.setStroke(Values.circleStroke);

        pane.getChildren().clear();

        // Label circle with weight of the GraphNode
        Text text = new Text(Integer.toString(weights));

        pane.getChildren().addAll(rectangle, text);
        pane.setTranslateX(x - Values.nodeRadius);
        pane.setTranslateY(y - Values.nodeRadius);
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


        return pane;
    }

    /**
     * Redraws Shape
     */
    public void updateObject() {
        group.getChildren().clear();
        group.getChildren().add(drawObject());
        updateEdges();

    }

    /**
     * helper for updateObject
     */
    private void updateEdges() {
        for (GraphEdge currentEdge : graphEdges) {
            currentEdge.drawObject();
        }
    }

}
