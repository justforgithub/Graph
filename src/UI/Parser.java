package UI;

import Data.AGraphNode;
import Data.Graph;
import Data.GraphEdge;
import Data.Values;
import javafx.scene.layout.Pane;

import java.io.*;

/**
 * Created by Deviltech on 19.05.2016.
 */
public class Parser {

    /**
     * Read in File and generate Graph
     * @param filePath
     * @param myGraph
     * @return
     * @throws FileNotFoundException
     */
    public static Graph readInFile(File filePath, Graph myGraph, Pane drawPane) throws FileNotFoundException {

        // Checks if file exists
        if (!filePath.exists()) {
            throw new FileNotFoundException(Values.fileNotFound + filePath);
        }

        myGraph.reset();

        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            try {
                String line = null;
                drawPane.getChildren().clear();

                while ((line = input.readLine()) != null) {
                    if(line.startsWith("NODE")){
                        generateNodeFromString(line, myGraph, drawPane);
                    }
                    if(line.startsWith("EDGE")){
                        generateEdgeFromString(line, myGraph, drawPane);
                    }

                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return myGraph;

    }

    /**
     * Write graph to file
     * @param filePath
     * @param myGraph
     */
    public static void writeToFile(File filePath, Graph myGraph){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(filePath);
            fileWriter.write(generateStringFromGraph(myGraph));

            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Read in line and generate Node based on string
     * @param s
     * @return
     */
    private static AGraphNode generateNodeFromString(String s, Graph graph, Pane pane){
        String[] arg;
        arg = s.split("\\\t");
        Values.NodeState currentState = generateNodeTypeFromString(arg[1]);
        double x = Double.parseDouble(arg[2]);
        double y = Double.parseDouble(arg[3]);
        AGraphNode node = graph.generateGraphNodeByState(x,y, currentState);
        pane.getChildren().add(node.getGroup());
        return node;
    }

    /**
     * Read in line and generate Line based on string
     * @param s
     * @param graph
     * @return
     */
    private static GraphEdge generateEdgeFromString(String s, Graph graph, Pane pane){
        String[] arg;
        arg = s.split("\\\t");
        int weight = Integer.parseInt(arg[1]);
        AGraphNode originNode = graph.graphNodes.get(Integer.parseInt(arg[2]));
        AGraphNode directionNode = graph.graphNodes.get(Integer.parseInt(arg[3]));
        GraphEdge edge = graph.generateGraphEdge(weight, originNode, directionNode);
        pane.getChildren().add(edge.getGroup());
        originNode.updateObject();
        directionNode.updateObject();
        return edge;
    }

    /**
     * Get NodeState from String
     * @param s
     * @return
     */
    private static Values.NodeState generateNodeTypeFromString(String s){
        switch (s) {
            case "C":
                return Values.NodeState.CONVERSION;
            case "I":
                return Values.NodeState.INPUT;
            case "O":
                return Values.NodeState.OUTPUT;
            default:
                return Values.NodeState.STANDARD;
        }
    }

    /**
     * Generate String from Nodes and edges for Parser
     * @param graph
     * @return
     */
    public static String generateStringFromGraph(Graph graph){
        String s = "";
        for (AGraphNode currentNode : graph.graphNodes){
            s += currentNode.toString();
        }
        s += "\n";
        for (GraphEdge currentEdge : graph.graphEdges){
            s += currentEdge.toString();
        }
        return s;
    }
}
