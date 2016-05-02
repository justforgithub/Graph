package Data;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class GraphEdge {

    private int weight;
    private GraphNode originGraphNode;
    private GraphNode directionGraphNode;
    private Graph graph;
    private Group group;



    // Constructor
    public GraphEdge(int weight, GraphNode originGraphNode, GraphNode directionGraphNode, Graph graph) {
        this.weight = weight;
        this.originGraphNode = originGraphNode;
        this.directionGraphNode = directionGraphNode;
        this.graph = graph;
        this.group = new Group(drawObject(weight<2? Values.Weight1Color: Values.Weight2Color));

        originGraphNode.addEdge(this);
        directionGraphNode.addEdge(this);
        directionGraphNode.updateObject();

    }

    public int getWeight() {
        return weight;
    }

    public GraphNode getOriginGraphNode() {
        return originGraphNode;
    }

    public GraphNode getDirectionGraphNode() {
        return directionGraphNode;
    }

    public Group getGroup(){
        return group;
    }

    private Group drawObject(Color lineColor){
        Pane originPane = originGraphNode.pane;
        Pane directionPane = directionGraphNode.pane;
        Line line = new Line();
        line.setStroke(lineColor);
        line.setFill(lineColor);
        line.startXProperty().bind(originPane.translateXProperty());
        line.startYProperty().bind(originPane.translateYProperty());
        line.endXProperty().bind(directionPane.translateXProperty());
        line.endYProperty().bind(directionPane.translateYProperty());

        Circle arrow = new Circle(Values.arrowRadius, lineColor);
        arrow.centerXProperty().bind(directionPane.translateXProperty());
        arrow.centerYProperty().bind(directionPane.translateYProperty());

        Group group = new Group();
        group.getChildren().addAll(line, arrow);

        return group;

    }
}
