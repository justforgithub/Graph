package Data;

import javafx.scene.Group;

/**
 * Created by Deviltech on 19.05.2016.
 */
public class OutputGraphNode extends AGraphNode {


    // Constructor for abstract class
    public OutputGraphNode(Graph graph, double x, double y) {
        super(graph, x, y);

    }

    @Override
    public boolean isSatisfied() {
        boolean isSatisfied = true;
        for (GraphEdge currentGraphEdge : graphEdges) {
            // All edges have to be outgoing
            if (!this.equals(currentGraphEdge.getOriginGraphNode())) {
                isSatisfied = false;
                break;
            }
        }
        return isSatisfied;
    }

    @Override
    public Group generateBackgroundShape(double x, double y, double radius) {
        return generateStandardBackgroundShape(x, y, radius);
    }

    @Override
    public String generateNodeText() {
        return Values.outputNodeText;
    }
}
