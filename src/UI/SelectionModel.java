package UI;

import javafx.scene.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Deviltech on 09.05.2016.
 */
public class SelectionModel {

    Set<Node> selection = new HashSet<>();


    public void add(Node node) {
        node.setStyle("-fx-effect: dropshadow(three-pass-box, red, 3, 3, 0, 0);");
        selection.add(node);
    }

    public void remove(Node node) {
        node.setStyle("-fx-effect: null");
        selection.remove(node);
    }

    public void clear() {

        while (!selection.isEmpty()) {
            remove(selection.iterator().next());
        }

    }

    public boolean contains(Node node) {
        return selection.contains(node);
    }

    public void log() {
        System.out.println("Items in model: " + Arrays.asList(selection.toArray()));
    }

    public Iterator<Node> getIterator(){
        return selection.iterator();
    }

}

