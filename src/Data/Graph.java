package Data;

import java.util.ArrayList;

/**
 * Created by Deviltech on 02.05.2016.
 */
public class Graph {

    public ArrayList<GraphEdge> graphEdges;
    public ArrayList<GraphNode> graphNodes;
    public Enum graphState;


    public Graph(){
        graphEdges = new ArrayList<>();
        graphNodes = new ArrayList<>();
        graphState = Values.PaneState.IDLE;
    }

    public void generateGraphNode(double x, double y){
        GraphNode graphNode = new GraphNode(this, x, y);
        graphNodes.add(graphNode);
    }

    public void generateGraphEdge(int weight,GraphNode originGraphNode,GraphNode directionGraphNode){
        GraphEdge graphEdge = new GraphEdge(weight, originGraphNode, directionGraphNode, this);
        graphEdges.add(graphEdge);
    }






}
