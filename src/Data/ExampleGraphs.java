package Data;

/**
 * Created by Deviltech on 03.05.2016.
 */
public class ExampleGraphs {

    public static void example1(Graph graph){


        graph.generateStandardGraphNode(200, 200);
        graph.generateStandardGraphNode(400, 400);
        graph.generateStandardGraphNode(300, 600);
        graph.generateStandardGraphNode(500, 300);
        graph.generateGraphEdge(Values.standardWeight2, graph.graphNodes.get(0), graph.graphNodes.get(1));
        graph.generateGraphEdge(Values.standardWeight1, graph.graphNodes.get(1), graph.graphNodes.get(2));
        graph.generateGraphEdge(Values.standardWeight1, graph.graphNodes.get(3), graph.graphNodes.get(1));
        graph.generateGraphEdge(Values.standardWeight1, graph.graphNodes.get(1), graph.graphNodes.get(1));

    }

    public static void conversionExample(Graph graph){
        AGraphNode leftUp = graph.generateOutputGraphNode(50, 50);
        AGraphNode rightUp = graph.generateStandardGraphNode(250, 50);
        AGraphNode mid = graph.generateStandardGraphNode(150, 150);
        AGraphNode botLeft = graph.generateInputGraphNode(50, 250);
        AGraphNode botMid = graph.generateStandardGraphNode(150, 250);
        AGraphNode botRight = graph.generateOutputGraphNode(250, 250);

        AGraphNode conv1 = graph.generateInputGraphNode(50, 400);
        AGraphNode conv2 = graph.generateConversionGraphNode(150, 400);
        AGraphNode conv3 = graph.generateOutputGraphNode(250, 400);

        graph.generateGraphEdge(1, mid, leftUp);
        graph.generateGraphEdge(2, rightUp, mid);
        graph.generateGraphEdge(2, rightUp, rightUp);
        graph.generateGraphEdge(1, mid, botMid);
        graph.generateGraphEdge(1, botLeft, botMid);
        graph.generateGraphEdge(2, botMid, botRight);

        graph.generateGraphEdge(1, conv1, conv2);
        graph.generateGraphEdge(2, conv2, conv3);

    }
}
