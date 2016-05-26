package Data;

import javafx.scene.paint.Color;

/**
 * Created by Deviltech on 28.04.2016.
 */
public class Values {

    public static final double paneWidth = 900;

    public static final double paneHeigth = 750;

    public static final double nodeRadius = 25.0;

    public static final double arrowRadius = 10.0;

    public static final double lineStroke = 3.0;

    public static final double lineStroke2 = lineStroke *2;

    public static final Color circleFill = Color.WHITE;

    public static final Color circleFillsat = Color.LIGHTGREEN;

    public static final Color circleFillunsat = Color.WHITE;

    public static final Color circleStroke = Color.BLACK;

    public static final Color Weight1Color = Color.DARKRED;

    public static final Color Weight2Color = Color.DARKBLUE;

    public enum PaneState {IDLE, GRAPHEDGE, GRAPHNODE, SELECTED};

    public enum NodeState {STANDARD, CONVERSION, INPUT, OUTPUT};

    public static final int standardWeight1 = 1;

    public static final int standardWeight2 = 2;

    public static final int standardSatisfied = standardWeight2;

    public static final boolean isInvalidEdgeSwapAllowed = false;

    public static final String weightButtonSelected = "Edge Weight: " + Integer.toString(standardWeight1);

    public static final String weightButtonUnselected = "Edge Weight: " + Integer.toString(standardWeight2);

    public static final String outputNodeText = "OUT";

    public static final String inputNodeText = "IN";

    public static final String fileNotFound = "File not found: ";

    public static final String standardText = "Change Edge Direction: RIGHT CLICK on edge arrow \n"
            + "Toggle Edge weight: SHIFT + RIGHT CLICK on edge arrow";

    public static final String conversionToolTipText = "Conversion Node \n"  + "Converts an Edge with weight "
            + Integer.toString(standardWeight1) + " to weight " + Integer.toString(standardWeight2)
            + ".\nIs satisfied with incoming weight of at least " + Integer.toString(standardWeight1) + ".";

    public static final String standardToolTipText =  "Standard Node \n" + "Is satisfied with incoming edge weight of "
            + Integer.toString(standardSatisfied) + ".";

    public static final String outputToolTipText = "Satisfied Out Node \n" + "Is satisfied when all edges are incoming.";

    public static final String inputToolTipText = "Satisfied In Node \n" + "Is satisfied when all edges are outgoing.";

    public static final String rightClickEdgeText = "<- Right click on arrow to change the direction of an edge";


}
