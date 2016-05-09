package Data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
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
    private AGraphNode originGraphNode;
    private AGraphNode directionGraphNode;
    private Graph graph;
    private Group group;



    // Constructor
    public GraphEdge(int weight, AGraphNode originGraphNode, AGraphNode directionGraphNode, Graph graph) {
        this.weight = weight;
        this.originGraphNode = originGraphNode;
        this.directionGraphNode = directionGraphNode;
        this.graph = graph;
        this.group = drawObject();

        originGraphNode.addEdge(this);
        directionGraphNode.addEdge(this);
        directionGraphNode.updateObject();

    }

    public int getWeight() {
        return weight;
    }

    public AGraphNode getOriginGraphNode() {
        return originGraphNode;
    }

    public AGraphNode getDirectionGraphNode() {
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

        Color lineColor = weight < 2 ? Values.Weight1Color : Values.Weight2Color;
        Pane originPane = originGraphNode.getPane();
        Pane directionPane = directionGraphNode.getPane();

        Group edgeGroup = new Group();


        // draw Arrow
        Polygon arrow = new Polygon();
        arrow.setFill(lineColor);
        arrow.getPoints().addAll(new Double[]{
                0.0, Values.arrowRadius,
                -Values.arrowRadius, -Values.arrowRadius,
                Values.arrowRadius, -Values.arrowRadius});

        // Edge to the own Node
        boolean isCircledEdge = originGraphNode.equals(directionGraphNode);

        if(isCircledEdge){
            // Circle for circled edge
            Circle circle = new Circle(Values.nodeRadius*0.75, Color.TRANSPARENT);
            circle.setStroke(lineColor);
            circle.setStrokeWidth(Values.lineStroke);

            // not allowed to be clickable
            circle.setMouseTransparent(true);

            // Property for loop circle and arrow X center
            DoubleProperty circleCenterX = new SimpleDoubleProperty();
            // Property for loop circle Y center
            DoubleProperty circleCenterY = new SimpleDoubleProperty();
            // Property for arrow Y center
            DoubleProperty arrowCenterY = new SimpleDoubleProperty();



            // calculate values for shapes
            circleCenterX.set(originPane.getTranslateX()+0.5*Values.nodeRadius);
            circleCenterY.set(originPane.getTranslateY());
            arrowCenterY.set(originPane.getTranslateY() - Values.nodeRadius + Values.arrowRadius*0.5);




            // listener for node movement
            ChangeListener circleListener = (a, b, c) ->{
                circleCenterX.set(originPane.getTranslateX()+0.5*Values.nodeRadius);
                circleCenterY.set(originPane.getTranslateY());
                arrowCenterY.set(originPane.getTranslateY() - Values.nodeRadius + Values.arrowRadius*0.5);
            };

            // bind circle and arrow to node movement
            originPane.translateXProperty().addListener(circleListener);
            originPane.translateYProperty().addListener(circleListener);

            circle.centerXProperty().bind(circleCenterX);
            circle.centerYProperty().bind(circleCenterY);

            arrow.setRotate(90);
            arrow.translateXProperty().bind(circleCenterX);
            arrow.translateYProperty().bind(arrowCenterY);

            // Arrow keyhandler for direction toggle
            arrow.setOnMousePressed((event) -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (event.isShiftDown()) {
                        toggleWeight();
                    } else {
                        // no function, just for user feedback
                        arrow.setRotate(arrow.getRotate() - 180);
                    }
                }
            });
            edgeGroup.getChildren().addAll(circle, arrow);

        // normal edge
        } else {


            Line line = new Line();
            line.setStroke(lineColor);
            line.setFill(lineColor);
            line.setStrokeWidth(Values.lineStroke);

            // not allowed to be clickable
            line.setMouseTransparent(true);

            // Properties for arrow
            DoubleProperty centerX = new SimpleDoubleProperty();
            DoubleProperty centerY = new SimpleDoubleProperty();
            DoubleProperty arrowAngle = new SimpleDoubleProperty();

            // Property for origin node Center X
            DoubleProperty originNodeCenterX = new SimpleDoubleProperty();
            // Property for origin node Center Y
            DoubleProperty originNodeCenterY = new SimpleDoubleProperty();

            // Property for direction node Center X
            DoubleProperty directionNodeCenterX = new SimpleDoubleProperty();
            // Property for origin node Center Y
            DoubleProperty directionNodeCenterY = new SimpleDoubleProperty();


            originNodeCenterX.set(originPane.getTranslateX() + originPane.getWidth()*0.5);
            originNodeCenterY.set(originPane.getTranslateY() + originPane.getHeight()*0.5);

            directionNodeCenterX.set(directionPane.getTranslateX() + directionPane.getWidth()*0.5);
            directionNodeCenterY.set(directionPane.getTranslateY() + directionPane.getHeight()*0.5);

            // Change Arrow every time the line changes
            ChangeListener arrowListener = (a, b, c) -> {
                centerX.set((line.startXProperty().doubleValue() + line.endXProperty().doubleValue()) / 2);
                centerY.set((line.startYProperty().doubleValue() + line.endYProperty().doubleValue()) / 2);
                arrowAngle.set((Math.atan2(line.endYProperty().doubleValue() - line.startYProperty().doubleValue(), line.endXProperty().doubleValue() - line.startXProperty().doubleValue()) * 180 / 3.14));

                originNodeCenterX.set(originPane.getTranslateX() + originPane.getWidth()*0.5);
                originNodeCenterY.set(originPane.getTranslateY() + originPane.getHeight()*0.5);

                directionNodeCenterX.set(directionPane.getTranslateX() + directionPane.getWidth()*0.5);
                directionNodeCenterY.set(directionPane.getTranslateY() + directionPane.getHeight()*0.5);

            };


            // bind the line properties
            line.startXProperty().bind(originNodeCenterX);
            line.startYProperty().bind(originNodeCenterY);
            line.endXProperty().bind(directionNodeCenterX);
            line.endYProperty().bind(directionNodeCenterY);

            line.startXProperty().addListener(arrowListener);
            line.startYProperty().addListener(arrowListener);
            line.endXProperty().addListener(arrowListener);
            line.endYProperty().addListener(arrowListener);



            double endy = line.getEndY();
            double endx = line.getEndX();
            double starty = line.getStartY();
            double startx = line.getStartX();


            double angle = Math.atan2(endy - starty, endx - startx) * 180 / 3.14;

            arrow.setRotate((angle - 90));

            arrow.setTranslateX((startx + endx) / 2);
            arrow.setTranslateY((starty + endy) / 2);


            centerX.addListener((value) -> {
                arrow.setTranslateX(centerX.doubleValue());
            });

            centerY.addListener((value) -> {
                arrow.setTranslateY(centerY.doubleValue());
            });

            arrowAngle.addListener((value) -> {
                arrow.setRotate(arrowAngle.doubleValue() - 90);
            });

            // Arrow keyhandler for direction toggle
            arrow.setOnMousePressed((event) -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (event.isShiftDown()) {
                        toggleWeight();
                    } else {
                        swapEdgeDirection();
                    }
                }
            });

            edgeGroup.getChildren().addAll(line, arrow);
        }
        return edgeGroup;

    }


    /**
     * swaps the arrow direction of the edge
     */
    public void swapEdgeDirection(){
        AGraphNode oldOrigin = originGraphNode;
        AGraphNode oldDirection = directionGraphNode;
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
