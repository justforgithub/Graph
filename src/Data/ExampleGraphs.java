package Data;

/**
 * Created by Deviltech on 03.05.2016.
 */
public class ExampleGraphs {

    public static  void example1(Graph graph){

        graph.reset();

        graph.generateGraphNode(200, 200);
        graph.generateGraphNode(400, 400);
        graph.generateGraphNode(300, 600);
        graph.generateGraphNode(500, 300);
        graph.generateGraphEdge(2, graph.graphNodes.get(0), graph.graphNodes.get(1));
        graph.generateGraphEdge(1, graph.graphNodes.get(1), graph.graphNodes.get(2));
        graph.generateGraphEdge(1, graph.graphNodes.get(3), graph.graphNodes.get(1));
        graph.generateGraphEdge(1, graph.graphNodes.get(1), graph.graphNodes.get(1));

    }
}
