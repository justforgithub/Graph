package Data;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;

/**
 * Created by Deviltech on 19.05.2016.
 */
public class OutputGraphNode extends AGraphNode {


    // Constructor for abstract class
    public OutputGraphNode(Graph graph, double x, double y) {
        super(graph, x, y);

    }

    @Override
    public boolean isSatisfied(int weight) {
        boolean isSat = true;
        for (GraphEdge currentGraphEdge : graphEdges) {
            // All edges have to be outgoing
            if (!this.equals(currentGraphEdge.getDirectionGraphNode())) {
                isSat = false;
                break;
            }
        }
        // Return true, if all edges incoming or current incoming (design choice)
        return isSat || weight > 0;
    }

    @Override
    public Group generateBackgroundShape(double x, double y, double radius) {
        return generateStandardBackgroundShape(x, y, radius);
    }

    @Override
    public String generateNodeText() {
        return Values.outputNodeText;
    }

    @Override
    public String toString() {
        return toStringHelper("O");
    }

    @Override
    public boolean isInvalidSwapsAllowed() {
        return true;
    }

    @Override
    public Tooltip generateTooltip() {
        Tooltip tp = new Tooltip();
        tp.setText(Values.outputToolTipText);
        return tp;
    }
}
