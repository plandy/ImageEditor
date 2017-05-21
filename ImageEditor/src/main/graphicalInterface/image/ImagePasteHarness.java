package main.graphicalInterface.image;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagePasteHarness extends Pane {

    private final Node image;
    private List<Node> toolThingies = new ArrayList<Node>();

    public ImagePasteHarness( Node p_image ) {
        image = p_image;

        getChildren().add( image );
    }

    public void addToolThingy( Node p_node ) {
        toolThingies.add( p_node );
        getChildren().add( p_node );
    }

    public void addAllToolThingies( Node... p_nodes ) {
        List<Node> thingies = Arrays.asList(p_nodes);
        toolThingies.addAll( thingies );
        getChildren().addAll( thingies );
    }

    public Node getImage() {
        return image;
    }

    public void removeToolThingies() {
        getChildren().removeAll( toolThingies );
        toolThingies.clear();
    }
}
