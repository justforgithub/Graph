package Data;

import java.util.ArrayList;
import Data.Values.*;
import javafx.scene.Group;

import static Data.Values.standardWeight;

/**
 * Created by Deviltech on 02.05.2016.
 */
public class Graph {

    public ArrayList<GraphEdge> graphEdges;
    public ArrayList<GraphNode> graphNodes;
    public PaneState graphState;
    public int graphWeigth;
    // Selcetion for Edge generation: first and second node
    public GraphNode firstNodeSelection;
    public GraphNode secondNodeSelection;


    public Graph(){
        reset();
    }

    /**
     * Generate Node at pos x, y
     * @param x
     * @param y
     * @return
     */
    public GraphNode generateGraphNode(double x, double y){
        GraphNode graphNode = new GraphNode(this, x, y);
        graphNodes.add(graphNode);
        return graphNode;
    }

    /**
     * Generate Edge between 2 Nodes with weight
     * @param weight
     * @param originGraphNode
     * @param directionGraphNode
     * @return
     */
    public GraphEdge generateGraphEdge(int weight,GraphNode originGraphNode,GraphNode directionGraphNode){
        GraphEdge graphEdge = new GraphEdge(weight, originGraphNode, directionGraphNode, this);
        graphEdges.add(graphEdge);
        return graphEdge;
    }

    /**
     * Check if there is already an edge between 2 nodes
     * @param node1
     * @param node2
     * @return
     */
    public boolean isEdgeExistent(GraphNode node1, GraphNode node2){
        boolean isEdge = false;
        for (GraphEdge currentEdge: graphEdges){
            if((currentEdge.getDirectionGraphNode().equals(node1) && currentEdge.getOriginGraphNode().equals(node2))
                    || (currentEdge.getDirectionGraphNode().equals(node2) && currentEdge.getOriginGraphNode().equals(node1))){
                isEdge = true;
                break;
            }
        }
        return isEdge;
    }

    /**
     * Completely reset the graph
     */
    public void reset(){
        graphEdges = new ArrayList<>();
        graphNodes = new ArrayList<>();
        graphWeigth = standardWeight;
        graphState = PaneState.IDLE;
        firstNodeSelection = null;
        secondNodeSelection = null;
    }






}
