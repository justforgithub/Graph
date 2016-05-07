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
        graph.generateStandardGraphNode(300, 300);
        graph.generateConversionGraphNode(400, 300);
        graph.generateStandardGraphNode(300, 500);
        graph.generateGraphEdge(Values.standardWeight1, graph.graphNodes.get(0), graph.graphNodes.get(1));
        graph.generateGraphEdge(Values.standardWeight2, graph.graphNodes.get(1), graph.graphNodes.get(2));


    }
}
