package Data;

import javafx.scene.paint.Color;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class Values {

    public static final double nodeRadius = 25.0;

    public static final double arrowRadius = 8.0;

    public static final double lineStroke = 2.0;

    public static final Color circleFill = Color.WHITE;

    public static final Color circleFillunsat = Color.YELLOW;

    public static final Color circleStroke = Color.BLACK;

    public static final Color Weight1Color = Color.DARKRED;

    public static final Color Weight2Color = Color.DARKBLUE;

    public enum PaneState {IDLE, GRAPHEDGE, GRAPHNODE, SELECTED};

    public enum NodeState {STANDARD, CONVERSION, INPUT, OUTPUT};

    public static final int standardWeight1 = 1;

    public static final int standardWeight2 = 2;

    public static final int standardSatisfied = standardWeight2;

    public static final String weightButtonSelected = "Edge Weight: 1";

    public static final String weightButtonUnselected = "Edge Weight: 2";

    public static final String outputNodeText = "OUT";

    public static final String inputNodeText = "IN";

    public static final String fileNotFound = "File not found: ";

}
