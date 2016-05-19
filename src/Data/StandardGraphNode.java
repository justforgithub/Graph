package Data;


import javafx.scene.Group;

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

      return generateStandardBackgroundShape(x, y, radius);
    }

    @Override
    public String generateNodeText() {
        return Integer.toString(getIncomingWeights());
    }

}
