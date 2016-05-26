package Data;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by Deviltech on 03.05.2016.
 */
public class ExampleGraphs {

    public static void example1(Graph graph, Pane pane){

        AGraphNode nodeIn = graph.generateInputGraphNode(50, 150);
        AGraphNode node1 = graph.generateStandardGraphNode(150, 150);
        AGraphNode node2 = graph.generateStandardGraphNode(250, 150);
        AGraphNode node3 = graph.generateStandardGraphNode(250, 250);
        AGraphNode node4 = graph.generateStandardGraphNode(350, 50);
        AGraphNode node5 = graph.generateStandardGraphNode(450, 150);
        AGraphNode nodeOut = graph.generateOutputGraphNode(550, 150);

        graph.generateGraphEdge(1, node1, nodeIn);
        graph.generateGraphEdge(1, node1, node1);
        graph.generateGraphEdge(2, node2, node1);
        graph.generateGraphEdge(1, node1, node3);
        graph.generateGraphEdge(1, node3, node5);
        graph.generateGraphEdge(1, node4, node2);
        graph.generateGraphEdge(1, node4, node4);
        graph.generateGraphEdge(1, node5, node4);
        graph.generateGraphEdge(1, node2, node5);
        graph.generateGraphEdge(1, node5, nodeOut);

        AGraphNode nodeText1 = graph.generateStandardGraphNode(50, 400);
        AGraphNode nodeText2 = graph.generateStandardGraphNode(50, 600);
        graph.generateGraphEdge(2, nodeText1, nodeText2);
        graph.generateGraphEdge(2, nodeText1, nodeText1);
        graph.generateGraphEdge(2, nodeText2, nodeText2);

        Text text = new Text(Values.rightClickEdgeText);
        text.setX(80);
        text.setY(520);
        text.setFont(Font.font(text.getFont().getName(), 20));

        pane.getChildren().add(text);


    }

    public static void conversionExample(Graph graph, Pane pane){
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

    public static void terminatorExample(Graph graph, Pane pane){
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

        Text text1 = new Text("Forced Inward Red");
        text1.setX(280);
        text1.setY(700);
        text1.setFont(Font.font(text1.getFont().getName(), 20));

        Text text2 = new Text("Inconstrained Blue");
        text2.setX(150);
        text2.setY(20);
        text2.setFont(Font.font(text2.getFont().getName(), 20));

        Text text3 = new Text("Forced Inward Blue");
        text3.setX(450);
        text3.setY(20);
        text3.setFont(Font.font(text3.getFont().getName(), 20));

        pane.getChildren().addAll(text1, text2, text3);

    }

    public static void LatchExample(Graph graph, Pane pane){
        AGraphNode inNode = graph.generateInputGraphNode(100, 250);
        AGraphNode midNode = graph.generateStandardGraphNode(300, 250);
        AGraphNode topNode = graph.generateStandardGraphNode(400, 150);
        AGraphNode botNode = graph.generateStandardGraphNode(400, 350);
        AGraphNode topOutNode = graph.generateOutputGraphNode(600, 150);
        AGraphNode botOutNode = graph.generateOutputGraphNode(600, 350);

        graph.generateGraphEdge(2, midNode, inNode);
        graph.generateGraphEdge(2, topNode, midNode);
        graph.generateGraphEdge(2, midNode, botNode);
        graph.generateGraphEdge(1, botNode, topNode);
        graph.generateGraphEdge(1, topOutNode, topNode);
        graph.generateGraphEdge(1, botNode, botOutNode);

        Text text1 = new Text("Lock");
        text1.setX(200);
        text1.setY(240);
        text1.setFont(Font.font(text1.getFont().getName(), 20));

        Text text2 = new Text("State A");
        text2.setX(650);
        text2.setY(165);
        text2.setFont(Font.font(text2.getFont().getName(), 20));

        Text text3 = new Text("State B");
        text3.setX(650);
        text3.setY(365);
        text3.setFont(Font.font(text3.getFont().getName(), 20));

        pane.getChildren().addAll(text1, text2, text3);
    }

    public static void CrossoverExample(Graph graph, Pane pane){
        AGraphNode InLeft = graph.generateInputGraphNode(50, 250);
        AGraphNode InTop = graph.generateInputGraphNode(450, 50);
        AGraphNode InBot = graph.generateInputGraphNode(450, 450);
        AGraphNode InRight = graph.generateInputGraphNode(850, 250);

        AGraphNode LL = graph.generateStandardGraphNode(150, 250);
        AGraphNode LT = graph.generateStandardGraphNode(250, 150);
        AGraphNode LB = graph.generateStandardGraphNode(250, 350);
        AGraphNode LR = graph.generateStandardGraphNode(350, 250);

        AGraphNode MT = graph.generateStandardGraphNode(450, 150);
        AGraphNode MB = graph.generateStandardGraphNode(450, 350);

        AGraphNode RL = graph.generateStandardGraphNode(550, 250);
        AGraphNode RT = graph.generateStandardGraphNode(650, 150);
        AGraphNode RB = graph.generateStandardGraphNode(650, 350);
        AGraphNode RR = graph.generateStandardGraphNode(750, 250);

        graph.generateGraphEdge(2, InLeft, LL);
        graph.generateGraphEdge(1, LL, LT);
        graph.generateGraphEdge(1, LL, LB);
        graph.generateGraphEdge(1, LT, LB);
        graph.generateGraphEdge(1, LT, LR);
        graph.generateGraphEdge(1, LB, LR);

        graph.generateGraphEdge(2, InTop, MT);
        graph.generateGraphEdge(2, MB, InBot);
        graph.generateGraphEdge(2, LR, RL);
        graph.generateGraphEdge(1, MT, LT);
        graph.generateGraphEdge(1, MT, RT);
        graph.generateGraphEdge(1, LB, MB);
        graph.generateGraphEdge(1, RB, MB);

        graph.generateGraphEdge(1, RL, RT);
        graph.generateGraphEdge(1, RT, RR);
        graph.generateGraphEdge(1, RL, RB);
        graph.generateGraphEdge(1, RT, RB);
        graph.generateGraphEdge(1, RB, RR);
        graph.generateGraphEdge(2, RR, InRight);


        }
}
