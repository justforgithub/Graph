package Data;


import javafx.scene.Group;
import javafx.scene.control.Tooltip;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class StandardGraphNode extends AGraphNode {


    // Constructor for abstract class
    public StandardGraphNode(Graph graph, double x, double y) {
        super(graph, x, y);

    }


    public boolean isSatisfied(int weight) {
        return (getIncomingWeights() + weight) >= Values.standardSatisfied;
    }

    public Group generateBackgroundShape(double x, double y, double radius) {

      return generateStandardBackgroundShape(x, y, radius);
    }

    @Override
    public String generateNodeText() {
        return Integer.toString(getIncomingWeights());
    }

    @Override
    public String toString() {
        return toStringHelper("S");
    }

    @Override
    public boolean isInvalidSwapsAllowed() {
        return false;
    }

    @Override
    public Tooltip generateTooltip() {
        Tooltip tp = new Tooltip();
        tp.setText(Values.standardToolTipText);
        return tp;
    }

}
