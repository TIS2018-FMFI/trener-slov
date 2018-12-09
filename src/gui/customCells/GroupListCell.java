package gui.customCells;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import data.Group;
import data.Lesson;
import gui.controllers.cellControllers.GroupListCellController;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class GroupListCell extends CustomCellBase<Group>{
	
	ListView<Group> listview;
	Lesson lesson;
	GroupListCellController controller;
	
	public GroupListCell(ListView<Group> listview, Lesson lesson) {
		this.listview = listview;
		this.lesson = lesson;
		ListCell<Group> thisCell = this;
		handleDragAndDrop(thisCell);
	}
	
	@Override
	protected void updateItem(Group group, boolean empty) {
		super.updateItem(group, empty);
        if(empty || group == null) {
        	setText(null);
            setGraphic(null);
            return;
        } 
        
        if (controller == null) {
        	controller = new GroupListCellController(listview, lesson, group);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
	
	private void handleDragAndDrop(ListCell<Group> thisCell) {
		setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            String cellStateSerialized = "";
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream so = new ObjectOutputStream(bo);
                so.writeObject((Group)getItem());
                so.flush();
                cellStateSerialized = bo.toString();
            } catch (Exception e) {
                System.err.println(e);
            }
 
            content.putString(cellStateSerialized);
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != thisCell &&
                   event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                ObservableList<Group> items = getListView().getItems();
                Group cellState = null;
                try {
                    byte b[] = db.getString().getBytes(); 
                    ByteArrayInputStream bi = new ByteArrayInputStream(b);
                    ObjectInputStream si = new ObjectInputStream(bi);
                    cellState = (Group) si.readObject();
                } catch (Exception e) {
                    System.err.println(e);
                }
                 
                int draggedIdx = items.indexOf(cellState);
                int thisIdx = items.indexOf(getItem());
                 
                items.set(draggedIdx, getItem());
                items.set(thisIdx, cellState);
 
                List<Group> itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(itemscopy);
 
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        setOnDragDone(DragEvent::consume);
	}
}
