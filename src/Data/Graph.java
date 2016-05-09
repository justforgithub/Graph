package Data;

import java.util.ArrayList;
import java.util.Iterator;

import Data.Values.*;
import UI.SelectionModel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import static Data.Values.standardWeight1;

/**
 * Created by Deviltech on 02.05.2016.
 */
public class Graph {

    public ArrayList<GraphEdge> graphEdges;
    public ArrayList<AGraphNode> graphNodes;
    public PaneState graphState;
    public int graphWeigth;
    // Selcetion for Edge generation: first and second node
    public AGraphNode firstNodeSelection;
    public AGraphNode secondNodeSelection;


    public Graph(){
        reset();
    }

    /**
     * Generate Standard Node at pos x, y
     * @param x
     * @param y
     * @return
     */
    public AGraphNode generateStandardGraphNode(double x, double y){
        AGraphNode graphNode = new StandardGraphNode(this, x + Values.nodeRadius , y + Values.nodeRadius);
        graphNodes.add(graphNode);
        return graphNode;
    }


    /**
     * Generate Conversion Graphnode at pos x, y
     * @param x
     * @param y
     * @return
     */
    public AGraphNode generateConversionGraphNode(double x, double y){
        AGraphNode graphNode = new ConversionGraphNode(this, x, y);
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
    public GraphEdge generateGraphEdge(int weight,AGraphNode originGraphNode,AGraphNode directionGraphNode){
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
    public boolean isEdgeExistent(AGraphNode node1, AGraphNode node2){
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
        graphWeigth = standardWeight1;
        graphState = PaneState.IDLE;
        firstNodeSelection = null;
        secondNodeSelection = null;
    }


    /**
     * Deletes all selected elements from pane and graph
     * @param selectionModel
     * @param drawPane
     */
    public void deleteSelectedElements(SelectionModel selectionModel, Pane drawPane) {
        for (Iterator<Node> selectionIter = selectionModel.getIterator(); selectionIter.hasNext(); ) {
            Node node = selectionIter.next();
            System.out.println(node);
            Group g = (Group) node;
            System.out.println(g.getChildren().size());
            // TODO: Remove Node


            ArrayList<GraphEdge> toBeDeleted = new ArrayList<>();
            // remove edge
            for(GraphEdge currentGraphEdge: graphEdges){
                if(node.equals(currentGraphEdge.getGroup())){
                    currentGraphEdge.getDirectionGraphNode().removeEdge(currentGraphEdge);
                    currentGraphEdge.getOriginGraphNode().removeEdge(currentGraphEdge);
                    // Add to list to delete from graph later
                    toBeDeleted.add(currentGraphEdge);
                    drawPane.getChildren().remove(currentGraphEdge.getGroup());
                }
            }
            // delete from graph
            for(GraphEdge currentDelete: toBeDeleted){
                graphEdges.remove(currentDelete);
            }
        }

    }
}

