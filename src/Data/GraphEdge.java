package Data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        this.group = new Group(drawObject());

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

    /**
     * draw the Shape with color according to the weigth
     * @return
     */
    public Group drawObject(){

        Color lineColor = weight<2? Values.Weight1Color: Values.Weight2Color;
        Pane originPane = originGraphNode.getPane();
        Pane directionPane = directionGraphNode.getPane();
        Line line = new Line();
        line.setStroke(lineColor);
        line.setFill(lineColor);
        line.setStrokeWidth(Values.lineStroke);

        // bind the line properties
        line.startXProperty().bind(originPane.translateXProperty());
        line.startYProperty().bind(originPane.translateYProperty());
        line.endXProperty().bind(directionPane.translateXProperty());
        line.endYProperty().bind(directionPane.translateYProperty());

        DoubleProperty centerX = new SimpleDoubleProperty();
        DoubleProperty centerY = new SimpleDoubleProperty();
        DoubleProperty arrowAngle = new SimpleDoubleProperty();

        // Change Arrow every time the line changes
        ChangeListener arrowListener = (a, b, c)->  {
                centerX.set((line.startXProperty().doubleValue() + line.endXProperty().doubleValue())/2);
                centerY.set((line.startYProperty().doubleValue() + line.endYProperty().doubleValue())/2);
                arrowAngle.set((Math.atan2(line.endYProperty().doubleValue() - line.startYProperty().doubleValue(), line.endXProperty().doubleValue() - line.startXProperty().doubleValue()) * 180 / 3.14));

        };

        line.startXProperty().addListener(arrowListener);
        line.startYProperty().addListener(arrowListener);
        line.endXProperty().addListener(arrowListener);
        line.endYProperty().addListener(arrowListener);




        // draw Arrow
        Polygon arrow = new Polygon();
        arrow.setFill(lineColor);
        arrow.getPoints().addAll(new Double[]{
                0.0, Values.arrowRadius,
                -Values.arrowRadius, -Values.arrowRadius,
                Values.arrowRadius, -Values.arrowRadius});

        double endy = line.getEndY();
        double endx = line.getEndX();
        double starty = line.getStartY();
        double startx = line.getStartX();


        double angle = Math.atan2(endy - starty, endx - startx) * 180 / 3.14;

        arrow.setRotate((angle - 90));

        arrow.setTranslateX((startx + endx)/2);
        arrow.setTranslateY((starty + endy)/2);


        centerX.addListener((value)->{
            arrow.setTranslateX(centerX.doubleValue());
        });

        centerY.addListener((value)->{
            arrow.setTranslateY(centerY.doubleValue());
        });

        arrowAngle.addListener((value)->{
            arrow.setRotate(arrowAngle.doubleValue() - 90);
        });



        Group group = new Group();
        group.getChildren().addAll(line, arrow);

        return group;

    }


    /**
     * swaps the arrow direction of the edge
     */
    public void swapEdgeDirection(){
        GraphNode oldOrigin = originGraphNode;
        GraphNode oldDirection = directionGraphNode;
        originGraphNode = oldDirection;
        directionGraphNode = oldOrigin;
        originGraphNode.updateObject();
        directionGraphNode.updateObject();
        group.getChildren().clear();
        group.getChildren().addAll(drawObject().getChildren());

    }

    /**
     * toggle weight between 1 and 2
     */
    public void toggleWeight(){
        if(weight == 1){
            weight = 2;
        } else {
            weight = 1;
        }
        originGraphNode.updateObject();
        directionGraphNode.updateObject();
        group.getChildren().clear();
        group.getChildren().addAll(drawObject().getChildren());
    }
}
