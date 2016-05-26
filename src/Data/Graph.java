package Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Data.Values.*;
import UI.SelectionModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Created by Deviltech on 02.05.2016.
 */
public class Graph {

    public ArrayList<GraphEdge> graphEdges;
    public ArrayList<AGraphNode> graphNodes;
    public PaneState graphState;
    public NodeState nodeState;
    public SimpleIntegerProperty graphWeigth;
    public SimpleBooleanProperty isInvalidEdgeSwapAllowed;
    // Selection for Edge generation: first and second node
    public AGraphNode firstNodeSelection;
    public AGraphNode secondNodeSelection;
    public Text generalText;


    public Graph(){
        graphWeigth = new SimpleIntegerProperty();
        isInvalidEdgeSwapAllowed = new SimpleBooleanProperty();
        generalText = new Text();
        isInvalidEdgeSwapAllowed.set(Values.isInvalidEdgeSwapAllowed);
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
     * Generate Input Node at pos x, y
     * @param x
     * @param y
     * @return
     */
    public AGraphNode generateInputGraphNode(double x, double y){
        AGraphNode graphNode = new InputGraphNode(this, x + Values.nodeRadius , y + Values.nodeRadius);
        graphNodes.add(graphNode);
        return graphNode;
    }

    /**
     * generate Output Node at pos x, y
     * @param x
     * @param y
     * @return
     */
    public AGraphNode generateOutputGraphNode(double x, double y){
        AGraphNode graphNode = new OutputGraphNode(this, x + Values.nodeRadius , y + Values.nodeRadius);
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
        AGraphNode graphNode = new ConversionGraphNode(this, x + Values.nodeRadius, y + Values.nodeRadius);
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
        graphWeigth.set(Values.standardWeight1);
        graphState = PaneState.IDLE;
        nodeState = NodeState.STANDARD;
        firstNodeSelection = null;
        secondNodeSelection = null;
        generalText.setText(Values.standardText);
    }



    /**
     * Deletes all selected elements from pane and graph
     * @param selectionModel
     * @param drawPane
     */
    public void deleteSelectedElements(SelectionModel selectionModel, Pane drawPane) {
        for (Iterator<Node> selectionIter = selectionModel.getIterator(); selectionIter.hasNext(); ) {
            Node node = selectionIter.next();
            Group g = (Group) node;

            ArrayList<GraphEdge> toBeDeletedEdges = new ArrayList<>();
            ArrayList<AGraphNode> toBeDeletedNodes = new ArrayList<>();

            // remove edge
            for(GraphEdge currentGraphEdge: graphEdges){
                if(node.equals(currentGraphEdge.getGroup())){
                    currentGraphEdge.getDirectionGraphNode().removeEdge(currentGraphEdge);
                    currentGraphEdge.getOriginGraphNode().removeEdge(currentGraphEdge);
                    // Add to list to delete from graph later (to avoid iterator issues)
                    toBeDeletedEdges.add(currentGraphEdge);
                    drawPane.getChildren().remove(currentGraphEdge.getGroup());
                }
            }


            // remove node
            for(AGraphNode currentGraphNode: graphNodes){
                if(node.equals(currentGraphNode.getGroup())){
                    ArrayList<GraphEdge> toBeDeletedEdgesInNode = new ArrayList<>();
                    for(GraphEdge currentEdgeInNode: currentGraphNode.graphEdges){
                        // Remove all Edges in Node first before deletion
                        toBeDeletedEdgesInNode.add(currentEdgeInNode);
                    }
                    for (GraphEdge current: toBeDeletedEdgesInNode){
                        current.getOriginGraphNode().removeEdge(current);
                        current.getDirectionGraphNode().removeEdge(current);
                        toBeDeletedEdges.add(current);
                        drawPane.getChildren().remove(current.getGroup());
                    }
                    // Add to list to delete from graph later (to avoid iterator issues)
                    drawPane.getChildren().remove(currentGraphNode.getGroup());
                    toBeDeletedNodes.add(currentGraphNode);
                }
            }
            // delete edge from graph
            for(AGraphNode currentDelete: toBeDeletedNodes){
                graphNodes.remove(currentDelete);
            }

            // delete edge from graph
            for(GraphEdge currentDelete: toBeDeletedEdges){
                currentDelete.getDirectionGraphNode().updateObject();
                graphEdges.remove(currentDelete);
            }

        }
    }

    public List<Node> getGroups(){
        List<Node> lis = new ArrayList<>();
        for(AGraphNode node: graphNodes){
            lis.add(node.getGroup());
        }
        return lis;
    }

    /**
     * generate different Graph nodes based on Nodestate
     * @param x
     * @param y
     * @return
     */
    public AGraphNode generateGraphNodeByState(double x, double y, NodeState state){
        AGraphNode node = null;
        switch (state){
            case STANDARD:
                node =  generateStandardGraphNode(x,y);
                break;
            case CONVERSION:
                node = generateConversionGraphNode(x,y);
                break;
            case INPUT:
                node =  generateInputGraphNode(x,y);
                break;
            case OUTPUT:
                node = generateOutputGraphNode(x,y);
                break;
            default:
                break;

        }
        return node;
    }
}

