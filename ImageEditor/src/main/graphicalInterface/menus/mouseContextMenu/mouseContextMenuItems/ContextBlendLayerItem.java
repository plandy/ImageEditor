package main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import main.graphicalInterface.effects.PoissonBlend;
import main.graphicalInterface.image.ImageCanvas;
import main.graphicalInterface.image.ImageTab;

public class ContextBlendLayerItem extends MenuItem {

    public ContextBlendLayerItem() {
        super( "Blend Layer" );

        super.setOnAction( new ContextBlendLayerAction() );
    }

    private class ContextBlendLayerAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Object object = event.getSource();
            if ( object instanceof MenuItem ) {
                if ( object instanceof ContextBlendLayerItem ) {
                    ContextBlendLayerItem copyItem = (ContextBlendLayerItem) object;
                    ContextMenu menu = copyItem.getParentPopup();
                    Node node = menu.getOwnerNode();
                    if ( node instanceof ImageCanvas ) {
                        ImageCanvas imageCanvas = (ImageCanvas) node;

                        blendAction( imageCanvas );

                    }
                }
            }

        }

        private void blendAction( ImageCanvas p_selectedLayer ) {
            p_selectedLayer.getPastedNode();

            ImageTab parentTab = p_selectedLayer.getParentImageTab();
            ImageCanvas baseLayer = parentTab.getBaseLayer();

            if ( baseLayer != p_selectedLayer ) {
                p_selectedLayer.affixSelectionToLayer();
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                WritableImage foregroundImage = p_selectedLayer.snapshot(parameters,null);
                Image backgroundImage = baseLayer.snapshotCanvas();

                BlendRunner blendRunner = new BlendRunner( foregroundImage, backgroundImage, p_selectedLayer );
                blendRunner.start();
            }
        }

    }

    private class BlendRunner extends Thread {

        private final PoissonBlend poissonBlendTask;
        private final WritableImage foregroundImage;
        private final ImageCanvas selectedLayer;

        public BlendRunner( WritableImage p_foregroundImage, Image p_backgroundImage, ImageCanvas p_selectedLayer ) {

            poissonBlendTask = new PoissonBlend( p_foregroundImage, p_backgroundImage );

            foregroundImage = p_foregroundImage;
            selectedLayer = p_selectedLayer;

        }

        @Override
        public void run() {

            poissonBlendTask.start();

            while ( poissonBlendTask.isFinished() == false) {
                try {
                    Thread.sleep(1);
                }  catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Platform.runLater( () -> {
                selectedLayer.getGraphicsContext2D().drawImage(foregroundImage,0,0);
            } );

        }

    }
}
