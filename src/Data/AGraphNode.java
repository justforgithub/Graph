package Data;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
        this.group = drawObject(new Group());

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
        ArrayList<GraphEdge> toBeRemoved = new ArrayList<>();
        for (GraphEdge currentGraphEdge : graphEdges) {
            if (currentGraphEdge.equals(graphEdge)) {
                currentGraphEdge.getOriginGraphNode().pane.getChildren().remove(currentGraphEdge.getGroup());
                toBeRemoved.add(currentGraphEdge);
                currentGraphEdge.getOriginGraphNode().updateObject();
                currentGraphEdge.getDirectionGraphNode().updateObject();
            }
        }
        for(GraphEdge current: toBeRemoved){
            graphEdges.remove(current);
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
    private Group drawObject(Group group) {

        double radius = Values.nodeRadius;

        Rectangle rectangle = new Rectangle(radius, radius);


        // if satisfied, draw different fill color
        rectangle.setFill(isSatisfied()? Values.circleFill : Values.circleFillunsat);
        rectangle.setStroke(Values.circleStroke);

        // Label circle with weight of the StandardGraphNode
        Text text = new Text(generateNodeText());

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
        group.getChildren().add(pane);
        pane.toFront();

        return group;
    }

    /**
     * Redraws Shape
     */
    public void updateObject() {

        //group.getChildren().clear();
        updateEdges();
        this.group.getChildren().clear();
        this.group = drawObject(this.group);


    }

    /**
     * helper for updateObject
     */
    private void updateEdges() {
        for (GraphEdge currentEdge : graphEdges) {
            currentEdge.drawObject(currentEdge.getGroup());
            // Place Edge behind Node
            currentEdge.getGroup().toBack();
        }
    }

    public Group generateStandardBackgroundShape(double x, double y, double radius) {

        // Calculate radius for circle intersecting the rectangle corners
        double newRadius = Math.sqrt(0.5 * radius * radius);
        Circle circle = new Circle(x, y, newRadius);
        circle.setFill(Values.circleFill);
        circle.setStroke(Values.circleStroke);

        Group g = new Group();
        g.getChildren().addAll(circle);

        return g;
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

    /**
     * Generate the Label text for the Node
     * @return text
     */
    public abstract String generateNodeText();


}
