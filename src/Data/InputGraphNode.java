package Data;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;

/**
 * Created by Deviltech on 19.05.2016.
 */
public class InputGraphNode extends AGraphNode {

    // Constructor for abstract class
    public InputGraphNode(Graph graph, double x, double y) {
        super(graph, x, y);

    }

    @Override
    public boolean isSatisfied(int weight) {
        boolean isSat = true;
        for (GraphEdge currentGraphEdge : graphEdges) {
            // All edges have to be incoming
            if (!this.equals(currentGraphEdge.getOriginGraphNode())) {
                isSat = false;
                break;
            }
        }
        // Return true, if all edges outgoing or current outgoing (design choice)
        return isSat || weight < 0;
    }

    @Override
    public Group generateBackgroundShape(double x, double y, double radius) {
        return generateStandardBackgroundShape(x, y, radius);
    }

    @Override
    public String generateNodeText() {
        return Values.inputNodeText;
    }

    @Override
    public String toString() {
        return toStringHelper("I");
    }

    @Override
    public boolean isInvalidSwapsAllowed() {
        return true;
    }

    @Override
    public Tooltip generateTooltip() {
        Tooltip tp = new Tooltip();
        tp.setText(Values.inputToolTipText);
        return tp;
    }
}
