package main.graphicalInterface.menus.mouseContextMenu.mouseContextMenuItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import main.graphicalInterface.image.ImageCanvas;

public class ContextAffixPastedSelectionItem extends MenuItem {

    public ContextAffixPastedSelectionItem() {
        super( "Affix Pasted to Layer" );

        super.setOnAction( new ContextAffixPastedSelectionItem.ContextAffixPastedSelectionAction() );
    }

    private class ContextAffixPastedSelectionAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Object object = event.getSource();
            if ( object instanceof MenuItem ) {
                if ( object instanceof ContextAffixPastedSelectionItem ) {
                    ContextAffixPastedSelectionItem copyItem = (ContextAffixPastedSelectionItem) object;
                    ContextMenu menu = copyItem.getParentPopup();
                    Node node = menu.getOwnerNode();
                    if ( node instanceof ImageCanvas) {
                        ImageCanvas imageCanvas = (ImageCanvas) node;
                        if ( imageCanvas.getPastedNode() != null ) {
                            affix( imageCanvas );
                        }
                    }
                }
            }

        }

        private void affix( ImageCanvas p_canvas ) {
            p_canvas.affixSelectionToLayer();

        }
    }

}
