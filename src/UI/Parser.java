package UI;

import Data.AGraphNode;
import Data.Graph;
import Data.GraphEdge;
import Data.Values;

import java.io.*;

/**
 * Created by Deviltech on 19.05.2016.
 */
public class Parser {

    public Graph readInFile(File filePath, Graph myGraph) throws FileNotFoundException {

        // Checks if file exists
        if (!filePath.exists()) {
            throw new FileNotFoundException(Values.fileNotFound + filePath);
        }

        myGraph.reset();

        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    if(line.startsWith("NODE")){
                        generateNodeFromString(line, myGraph);
                    }
                    if(line.startsWith("EDGE")){
                        generateEdgeFromString(line, myGraph);
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
     * Read in line and generate Node based on string
     * @param s
     * @return
     */
    private AGraphNode generateNodeFromString(String s, Graph graph){
        String[] arg;
        arg = s.split("\\\t");
        Values.NodeState currentState = generateNodeTypeFromString(arg[1]);
        double x = Double.parseDouble(arg[3]);
        double y = Double.parseDouble(arg[4]);
        return graph.generateGraphNodeByState(x,y, currentState);
    }

    /**
     * Read in line and generate Line based on string
     * @param s
     * @param graph
     * @return
     */
    private GraphEdge generateEdgeFromString(String s, Graph graph){
        String[] arg;
        arg = s.split("\\\t");
        int weight = Integer.parseInt(arg[1]);
        AGraphNode originNode = graph.graphNodes.get(Integer.parseInt(arg[2]));
        AGraphNode directionNode = graph.graphNodes.get(Integer.parseInt(arg[3]));
        return graph.generateGraphEdge(weight, originNode, directionNode);
    }

    /**
     * Get NodeState from String
     * @param s
     * @return
     */
    private Values.NodeState generateNodeTypeFromString(String s){
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
}
