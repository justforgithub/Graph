package Data;


import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class StandardGraphNode extends AGraphNode {


    // Constructor for abstract class
    public StandardGraphNode(Graph graph, double x, double y) {
        super(graph, x, y);

    }


    public boolean isSatisfied() {
        return getIncomingWeights() >= 2;
    }

    public Group generateBackgroundShape(double x, double y, double radius) {

        // Calculate radius for circle intersecting the rectangle corners
        double newRadius = Math.sqrt(0.5 * radius * radius);
        Circle circle = new Circle(x, y, newRadius);
        circle.setFill(Values.circleFill);
        circle.setStroke(Values.circleStroke);

        Group g = new Group();
        g.getChildren().addAll(circle);

        return g;
    }

}
