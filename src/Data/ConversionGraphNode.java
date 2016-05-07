package Data;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Created by Deviltech on 07.05.2016.
 */
public class ConversionGraphNode extends AGraphNode {

    // Constructor for abstract class
    public ConversionGraphNode(Graph graph, double x, double y){
        super(graph, x, y);
    }

    @Override
    public boolean isSatisfied() {
       // conversion graph is already satisfied with less incoming weight
        return getIncomingWeights() + Values.standardWeight1 >= Values.standardSatisfied;
    }

    public Group generateBackgroundShape(double x, double y, double radius) {

        // Calculate radius for circle intersecting the rectangle corners
        double newRadius = Math.sqrt(0.5 * radius * radius);
        Circle circle = new Circle(x, y, newRadius);
        // Rectangle to be substracted from the circle for half circle
        Rectangle rectangle = new Rectangle(x, y - newRadius, newRadius, newRadius * 2);

        Shape leftShape = Shape.subtract(circle, rectangle);

        // move rectangle to the left to cover the other half circle
        rectangle.setX(x - newRadius);
        Shape rightShape = Shape.subtract(circle, rectangle);

        // Coloring
        leftShape.setFill(Values.Weight1Color);
        rightShape.setFill(Values.Weight2Color);
        //rectangle = new Rectangle(x - radius*0.5 ,y - radius*0.5, radius, radius);
        //rectangle.setFill(Values.circleFillunsat);

        Group g = new Group();
        g.getChildren().addAll(leftShape, rightShape);

        return g;
    }

}
