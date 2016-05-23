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

        AGraphNode conv1 = graph.generateInputGraphNode(50, 450);
        AGraphNode conv2 = graph.generateConversionGraphNode(150, 450);
        AGraphNode conv3 = graph.generateOutputGraphNode(250, 450);

        graph.generateGraphEdge(1, mid, leftUp);
        graph.generateGraphEdge(2, rightUp, mid);
        graph.generateGraphEdge(2, rightUp, rightUp);
        graph.generateGraphEdge(1, mid, botMid);
        graph.generateGraphEdge(1, botLeft, botMid);
        graph.generateGraphEdge(2, botMid, botRight);

        // Part 2

        graph.generateGraphEdge(1, conv1, conv2);
        graph.generateGraphEdge(2, conv2, conv3);

        // Part 3

        AGraphNode topNode1 = graph.generateInputGraphNode(400, 50);
        AGraphNode topNode2 = graph.generateStandardGraphNode(500, 50);
        AGraphNode topNode3 = graph.generateOutputGraphNode(600, 50);
        AGraphNode midTopNode = graph.generateStandardGraphNode(500, 150);
        AGraphNode midNode1 = graph.generateStandardGraphNode(400, 250);
        AGraphNode midNode2 = graph.generateStandardGraphNode(600, 250);
        AGraphNode midBotNode = graph.generateStandardGraphNode(500, 350);
        AGraphNode botNode1 = graph.generateInputGraphNode(400, 450);
        AGraphNode botNode2 = graph.generateStandardGraphNode(500, 450);
        AGraphNode botNode3 = graph.generateStandardGraphNode(600, 450);

        graph.generateGraphEdge(1, topNode1, topNode2);
        graph.generateGraphEdge(2, topNode2, topNode3);
        graph.generateGraphEdge(1, midTopNode, topNode2);
        graph.generateGraphEdge(1, midTopNode, midNode1);
        graph.generateGraphEdge(2, midNode2, midTopNode);
        graph.generateGraphEdge(2, midNode1, midNode2);
        graph.generateGraphEdge(1, midBotNode, midNode1);
        graph.generateGraphEdge(2, midNode2, midBotNode);
        graph.generateGraphEdge(1, midBotNode, botNode2);
        graph.generateGraphEdge(1, botNode1, botNode2);
        graph.generateGraphEdge(2, botNode2, botNode3);
    }

    public static void terminatorExample(Graph graph){
        // unconstrained blue
        AGraphNode inNode = graph.generateInputGraphNode(200, 50);
        AGraphNode topNode = graph.generateStandardGraphNode(200, 150);
        AGraphNode midLeftNode = graph.generateStandardGraphNode(100, 250);
        AGraphNode midMidNode = graph.generateStandardGraphNode(200, 250);
        AGraphNode midRightNode = graph.generateStandardGraphNode(300, 250);
        AGraphNode botNode = graph.generateStandardGraphNode(200, 350);

        graph.generateGraphEdge(2, topNode, inNode);
        graph.generateGraphEdge(2, midLeftNode, topNode);
        graph.generateGraphEdge(2, midLeftNode, midMidNode);
        graph.generateGraphEdge(2, midRightNode, midMidNode);
        graph.generateGraphEdge(2, midRightNode, topNode);
        graph.generateGraphEdge(2, midMidNode, botNode);
        graph.generateGraphEdge(2, botNode, midLeftNode);
        graph.generateGraphEdge(2, botNode, midRightNode);

        // forced inward blue
        AGraphNode inNode2 = graph.generateInputGraphNode(200+300, 50);
        AGraphNode topNode2 = graph.generateStandardGraphNode(200+300, 150);
        AGraphNode midLeftNode2 = graph.generateStandardGraphNode(100+300, 250);
        AGraphNode midMidNode2 = graph.generateStandardGraphNode(200+300, 250);
        AGraphNode midRightNode2 = graph.generateStandardGraphNode(300+300, 250);
        AGraphNode botNode2 = graph.generateStandardGraphNode(200+300, 350);

        graph.generateGraphEdge(2, inNode2, topNode2);
        graph.generateGraphEdge(2, topNode2, midLeftNode2);
        graph.generateGraphEdge(1, midLeftNode2, midMidNode2);
        graph.generateGraphEdge(1, midRightNode2, midMidNode2);
        graph.generateGraphEdge(2, topNode2, midRightNode2);
        graph.generateGraphEdge(2, midMidNode2, botNode2);
        graph.generateGraphEdge(1, botNode2, midLeftNode2);
        graph.generateGraphEdge(1, botNode2, midRightNode2);

        // forced red
        AGraphNode inNode3 = graph.generateInputGraphNode(200 + 150, 50+300);
        AGraphNode topNode3 = graph.generateStandardGraphNode(200 + 150, 150+300);
        AGraphNode midLeftNode3 = graph.generateStandardGraphNode(100 + 150, 250+ 300);
        AGraphNode midMidNode3 = graph.generateStandardGraphNode(200 + 150, 250 + 300);
        AGraphNode midRightNode3 = graph.generateStandardGraphNode(300 + 150, 250 + 300);
        AGraphNode botNode3 = graph.generateStandardGraphNode(200 + 150, 350 + 300);

        graph.generateGraphEdge(1, inNode3, topNode3);

    }

    public static void LatchExample(Graph graph){
        AGraphNode inNode = graph.generateInputGraphNode(200, 250);
        AGraphNode midNode = graph.generateStandardGraphNode(300, 250);
        AGraphNode topNode = graph.generateStandardGraphNode(400, 150);
        AGraphNode botNode = graph.generateStandardGraphNode(400, 350);
        AGraphNode topOutNode = graph.generateOutputGraphNode(500, 150);
        AGraphNode botOutNode = graph.generateOutputGraphNode(500, 350);

        graph.generateGraphEdge(2, midNode, inNode);
        graph.generateGraphEdge(2, topNode, midNode);
        graph.generateGraphEdge(2, midNode, botNode);
        graph.generateGraphEdge(1, botNode, topNode);
        graph.generateGraphEdge(1, topOutNode, topNode);
        graph.generateGraphEdge(1, botNode, botOutNode);
    }
}
