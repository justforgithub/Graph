package Data;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by Deviltech on 03.05.2016.
 */
public class ExampleGraphs {


    public static FadeTransition generateUnicorn(double x, double y, double scale){
        Image unicorn = new Image(Values.unicorn);
        ImageView unicornView = new ImageView();
        unicornView.setX(x*scale);
        unicornView.setY(y*scale);
        unicornView.setImage(unicorn);
        FadeTransition ft = new FadeTransition(Duration.millis(500), unicornView);
        ft.setFromValue(0.2);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);

        return ft;
    }


    public static void example1(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode nodeIn = graph.generateInputGraphNode(50*scale, 150*scale);
        AGraphNode node1 = graph.generateStandardGraphNode(150*scale, 150*scale);
        AGraphNode node2 = graph.generateStandardGraphNode(250*scale, 150*scale);
        AGraphNode node3 = graph.generateStandardGraphNode(250*scale, 250*scale);
        AGraphNode node4 = graph.generateStandardGraphNode(350*scale, 50*scale);
        AGraphNode node5 = graph.generateStandardGraphNode(450*scale, 150*scale);
        AGraphNode nodeOut = graph.generateOutputGraphNode(550*scale, 150*scale);

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

        AGraphNode nodeText1 = graph.generateStandardGraphNode(50*scale, 400*scale);
        AGraphNode nodeText2 = graph.generateStandardGraphNode(50*scale, 600*scale);
        graph.generateGraphEdge(2, nodeText1, nodeText2);
        graph.generateGraphEdge(2, nodeText1, nodeText1);
        graph.generateGraphEdge(2, nodeText2, nodeText2);

        Text text = new Text(Values.rightClickEdgeText);
        text.setX(80*scale);
        text.setY(520*scale);
        text.setFont(Font.font(text.getFont().getName(), 20));

        pane.getChildren().add(text);

        ArrayList<AGraphNode> graphNodes = new ArrayList<>();
        graphNodes.add(nodeIn);
        graphNodes.add(nodeOut);
        graphNodes.add(node1);
        graphNodes.add(node2);
        graphNodes.add(node3);
        graphNodes.add(node4);
        graphNodes.add(node5);

        graph.exerciseNodes = graphNodes;

        boolean[] bools = {true, true, true, true, true, true, true};
        graph.exerciseSolutions = bools;
        graph.isUnicornPossible = true;

    }

    public static void conversionExample(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode leftUp = graph.generateOutputGraphNode(50*scale, 50*scale);
        AGraphNode rightUp = graph.generateStandardGraphNode(250*scale, 50*scale);
        AGraphNode mid = graph.generateStandardGraphNode(150*scale, 150*scale);
        AGraphNode botLeft = graph.generateInputGraphNode(50*scale, 250*scale);
        AGraphNode botMid = graph.generateStandardGraphNode(150*scale, 250*scale);
        AGraphNode botRight = graph.generateOutputGraphNode(250*scale, 250*scale);

        AGraphNode conv1 = graph.generateInputGraphNode(50*scale, 450*scale);
        AGraphNode conv2 = graph.generateConversionGraphNode(150*scale, 450*scale);
        AGraphNode conv3 = graph.generateOutputGraphNode(250*scale, 450*scale);

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

        AGraphNode topNode1 = graph.generateInputGraphNode(400*scale, 50*scale);
        AGraphNode topNode2 = graph.generateStandardGraphNode(500*scale, 50*scale);
        AGraphNode topNode3 = graph.generateOutputGraphNode(600*scale, 50*scale);
        AGraphNode midTopNode = graph.generateStandardGraphNode(500*scale, 150*scale);
        AGraphNode midNode1 = graph.generateStandardGraphNode(400*scale, 250*scale);
        AGraphNode midNode2 = graph.generateStandardGraphNode(600*scale, 250*scale);
        AGraphNode midBotNode = graph.generateStandardGraphNode(500*scale, 350*scale);
        AGraphNode botNode1 = graph.generateInputGraphNode(400*scale, 450*scale);
        AGraphNode botNode2 = graph.generateStandardGraphNode(500*scale, 450*scale);
        AGraphNode botNode3 = graph.generateOutputGraphNode(600*scale, 450*scale);

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

        double scale = graph.exampleScale;

        // unconstrained blue
        AGraphNode inNode = graph.generateInputGraphNode(200*scale, 50*scale);
        AGraphNode topNode = graph.generateStandardGraphNode(200*scale, 150*scale);
        AGraphNode midLeftNode = graph.generateStandardGraphNode(100*scale, 250*scale);
        AGraphNode midMidNode = graph.generateStandardGraphNode(200*scale, 250*scale);
        AGraphNode midRightNode = graph.generateStandardGraphNode(300*scale, 250*scale);
        AGraphNode botNode = graph.generateStandardGraphNode(200*scale, 350*scale);

        graph.generateGraphEdge(2, topNode, inNode);
        graph.generateGraphEdge(2, midLeftNode, topNode);
        graph.generateGraphEdge(2, midLeftNode, midMidNode);
        graph.generateGraphEdge(2, midRightNode, midMidNode);
        graph.generateGraphEdge(2, midRightNode, topNode);
        graph.generateGraphEdge(2, midMidNode, botNode);
        graph.generateGraphEdge(2, botNode, midLeftNode);
        graph.generateGraphEdge(2, botNode, midRightNode);

        // forced inward blue
        AGraphNode inNode2 = graph.generateInputGraphNode(500*scale, 50*scale);
        AGraphNode topNode2 = graph.generateStandardGraphNode(500*scale, 150*scale);
        AGraphNode midLeftNode2 = graph.generateStandardGraphNode(400*scale, 250*scale);
        AGraphNode midMidNode2 = graph.generateStandardGraphNode(500*scale, 250*scale);
        AGraphNode midRightNode2 = graph.generateStandardGraphNode(600*scale, 250*scale);
        AGraphNode botNode2 = graph.generateStandardGraphNode(500*scale, 350*scale);

        graph.generateGraphEdge(2, inNode2, topNode2);
        graph.generateGraphEdge(2, topNode2, midLeftNode2);
        graph.generateGraphEdge(1, midLeftNode2, midMidNode2);
        graph.generateGraphEdge(1, midRightNode2, midMidNode2);
        graph.generateGraphEdge(2, topNode2, midRightNode2);
        graph.generateGraphEdge(2, midMidNode2, botNode2);
        graph.generateGraphEdge(1, botNode2, midLeftNode2);
        graph.generateGraphEdge(1, botNode2, midRightNode2);

        // forced red
        AGraphNode inNode3 = graph.generateInputGraphNode(350*scale, 350*scale);
        AGraphNode topNode3 = graph.generateStandardGraphNode(350*scale, 450*scale);
        AGraphNode midLeftNode3 = graph.generateStandardGraphNode(250*scale, 550*scale);
        AGraphNode midMidNode3 = graph.generateStandardGraphNode(350*scale, 550*scale);
        AGraphNode midRightNode3 = graph.generateStandardGraphNode(450*scale, 550*scale);
        AGraphNode botNode3 = graph.generateStandardGraphNode(350*scale, 650*scale);

        graph.generateGraphEdge(1, inNode3, topNode3);

        Text text1 = new Text("Forced Inward Red");
        text1.setX(280*scale);
        text1.setY(700*scale);
        text1.setFont(Font.font(text1.getFont().getName(), 20));

        Text text2 = new Text("Inconstrained Blue");
        text2.setX(150*scale);
        text2.setY(20*scale);
        text2.setFont(Font.font(text2.getFont().getName(), 20));

        Text text3 = new Text("Forced Inward Blue");
        text3.setX(450*scale);
        text3.setY(20*scale);
        text3.setFont(Font.font(text3.getFont().getName(), 20));

        pane.getChildren().addAll(text1, text2, text3);

    }

    public static void LatchExample(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode inNode = graph.generateInputGraphNode(100*scale, 250*scale);
        AGraphNode midNode = graph.generateStandardGraphNode(300*scale, 250*scale);
        AGraphNode topNode = graph.generateStandardGraphNode(400*scale, 150*scale);
        AGraphNode botNode = graph.generateStandardGraphNode(400*scale, 350*scale);
        AGraphNode topOutNode = graph.generateOutputGraphNode(600*scale, 150*scale);
        AGraphNode botOutNode = graph.generateOutputGraphNode(600*scale, 350*scale);

        graph.generateGraphEdge(2, midNode, inNode);
        graph.generateGraphEdge(2, topNode, midNode);
        graph.generateGraphEdge(2, midNode, botNode);
        graph.generateGraphEdge(1, botNode, topNode);
        graph.generateGraphEdge(1, topOutNode, topNode);
        graph.generateGraphEdge(1, botNode, botOutNode);

        Text text1 = new Text("Lock");
        text1.setX(200*scale);
        text1.setY(240*scale);
        text1.setFont(Font.font(text1.getFont().getName(), 20));

        Text text2 = new Text("State A");
        text2.setX(650*scale);
        text2.setY(165*scale);
        text2.setFont(Font.font(text2.getFont().getName(), 20));

        Text text3 = new Text("State B");
        text3.setX(650*scale);
        text3.setY(365*scale);
        text3.setFont(Font.font(text3.getFont().getName(), 20));

        pane.getChildren().addAll(text1, text2, text3);

        ArrayList<AGraphNode> graphNodes = new ArrayList<>();
        graphNodes.add(inNode);
        graphNodes.add(topOutNode);
        graphNodes.add(botOutNode);

        graph.exerciseNodes = graphNodes;

        boolean[] bools = {false, true, false};
        graph.exerciseSolutions = bools;
        graph.isUnicornPossible = true;
    }

    public static void CrossoverExample(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode InLeft = graph.generateInputGraphNode(50*scale, 250*scale);
        AGraphNode InTop = graph.generateInputGraphNode(450*scale, 50*scale);
        AGraphNode InBot = graph.generateInputGraphNode(450*scale, 450*scale);
        AGraphNode InRight = graph.generateInputGraphNode(850*scale, 250*scale);

        AGraphNode LL = graph.generateStandardGraphNode(150*scale, 250*scale);
        AGraphNode LT = graph.generateStandardGraphNode(250*scale, 150*scale);
        AGraphNode LB = graph.generateStandardGraphNode(250*scale, 350*scale);
        AGraphNode LR = graph.generateStandardGraphNode(350*scale, 250*scale);

        AGraphNode MT = graph.generateStandardGraphNode(450*scale, 150*scale);
        AGraphNode MB = graph.generateStandardGraphNode(450*scale, 350*scale);

        AGraphNode RL = graph.generateStandardGraphNode(550*scale, 250*scale);
        AGraphNode RT = graph.generateStandardGraphNode(650*scale, 150*scale);
        AGraphNode RB = graph.generateStandardGraphNode(650*scale, 350*scale);
        AGraphNode RR = graph.generateStandardGraphNode(750*scale, 250*scale);

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

        ArrayList<AGraphNode> graphNodes = new ArrayList<>();
        graphNodes.add(InLeft);
        graphNodes.add(InTop);
        graphNodes.add(InRight);
        graphNodes.add(InBot);

        graph.exerciseNodes = graphNodes;

        boolean[] bools = {false, false, true, true};
        graph.exerciseSolutions = bools;
        graph.isUnicornPossible = true;

        }

    public static void HalfCrossoverExample(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode InL = graph.generateInputGraphNode(50*scale, 350*scale);
        AGraphNode InT = graph.generateInputGraphNode(450*scale, 50*scale);
        AGraphNode InR = graph.generateInputGraphNode(850*scale, 350*scale);
        AGraphNode InB = graph.generateInputGraphNode(450*scale, 650*scale);

        AGraphNode ConL = graph.generateConversionGraphNode(150*scale, 350*scale);
        AGraphNode ConT = graph.generateConversionGraphNode(450*scale, 150*scale);
        AGraphNode ConR = graph.generateConversionGraphNode(750*scale, 350*scale);
        AGraphNode ConB = graph.generateConversionGraphNode(450*scale, 550*scale);

        AGraphNode Left = graph.generateStandardGraphNode(250*scale, 350*scale);
        AGraphNode Right = graph.generateStandardGraphNode(650*scale, 350*scale);

        AGraphNode TL = graph.generateStandardGraphNode(350*scale, 250*scale);
        AGraphNode TM = graph.generateStandardGraphNode(450*scale, 250*scale);
        AGraphNode TR = graph.generateStandardGraphNode(550*scale, 250*scale);

        AGraphNode BL = graph.generateStandardGraphNode(350*scale, 450*scale);
        AGraphNode BM = graph.generateStandardGraphNode(450*scale, 450*scale);
        AGraphNode BR = graph.generateStandardGraphNode(550*scale, 450*scale);

        graph.generateGraphEdge(1, InL, ConL);
        graph.generateGraphEdge(2, ConL, Left);
        graph.generateGraphEdge(1, TL, Left);
        graph.generateGraphEdge(1, TL, BL);
        graph.generateGraphEdge(1, Left, BL);
        graph.generateGraphEdge(1, InT, ConT);
        graph.generateGraphEdge(2, ConT, TM);
        graph.generateGraphEdge(2, TM, TL);
        graph.generateGraphEdge(2, TM, TR);
        graph.generateGraphEdge(1, ConB, InB);
        graph.generateGraphEdge(2, BM, ConB);
        graph.generateGraphEdge(2, BL, BM);
        graph.generateGraphEdge(2, BM, BR);
        graph.generateGraphEdge(1, ConR, InR);
        graph.generateGraphEdge(2, Right, ConR);
        graph.generateGraphEdge(1, TR, Right);
        graph.generateGraphEdge(1, TR, BR);
        graph.generateGraphEdge(1, BR, Right);

        ArrayList<AGraphNode> graphNodes = new ArrayList<>();
        graphNodes.add(InL);
        graphNodes.add(InT);
        graphNodes.add(InR);
        graphNodes.add(InB);

        graph.exerciseNodes = graphNodes;

        boolean[] bools = {false, false, true, true};
        graph.exerciseSolutions = bools;
        graph.isUnicornPossible = true;

    }

    public static void ProtectedOrExample(Graph graph, Pane pane){

        double scale = graph.exampleScale;

        AGraphNode InT = graph.generateInputGraphNode(450*scale, 50*scale);
        AGraphNode InL = graph.generateInputGraphNode(50*scale, 350*scale);
        AGraphNode InR = graph.generateInputGraphNode(850*scale, 350*scale);

        AGraphNode ConL = graph.generateConversionGraphNode(150*scale, 350*scale);
        AGraphNode ConR = graph.generateConversionGraphNode(750*scale, 350*scale);

        AGraphNode TL = graph.generateStandardGraphNode(250*scale, 150*scale);
        AGraphNode BL = graph.generateStandardGraphNode(250*scale, 350*scale);
        AGraphNode ML = graph.generateStandardGraphNode(350*scale, 250*scale);
        AGraphNode TM = graph.generateStandardGraphNode(450*scale, 150*scale);
        AGraphNode TR = graph.generateStandardGraphNode(650*scale, 150*scale);
        AGraphNode BR = graph.generateStandardGraphNode(650*scale, 350*scale);
        AGraphNode MR = graph.generateStandardGraphNode(550*scale, 250*scale);

        graph.generateGraphEdge(2, ConL, InL);
        graph.generateGraphEdge(1, BL, ConL);
        graph.generateGraphEdge(1, BL, TL);
        graph.generateGraphEdge(2, TL, ML);
        graph.generateGraphEdge(2, ML, BL);
        graph.generateGraphEdge(2, InT, TM);
        graph.generateGraphEdge(1, TM, TR);
        graph.generateGraphEdge(1, TM, TL);
        graph.generateGraphEdge(1, TR, BR);
        graph.generateGraphEdge(2, ML, MR);
        graph.generateGraphEdge(2, MR, TR);
        graph.generateGraphEdge(2, MR, BR);
        graph.generateGraphEdge(1, BR, ConR);
        graph.generateGraphEdge(2, ConR, InR);
    }
}
