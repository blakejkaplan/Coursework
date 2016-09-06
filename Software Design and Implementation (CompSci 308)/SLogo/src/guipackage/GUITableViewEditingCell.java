package guipackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * TableView editable cell
 */

public class GUITableViewEditingCell extends TableCell<GUITableViewTableVariable, Double>{
	private static final int TEXT_FIELD_MULTIPLIER = 2;
	private TextField textField;
	
	/**
	 * Lets the user edit the cell.
	 */
	@Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }
	
	/**
	 * Closes the edit when user clicks elsewhere.
	 */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setGraphic(null);
    }

    /**
     * Sets the TextField with the new inputted data.
     */
    @Override
    public void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }
    
    /**
     * Initializes TextField for each cell.
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* TEXT_FIELD_MULTIPLIER);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, 
                Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(Double.valueOf(textField.getText()));
                    }
            }
        });
    }
    
    /**
     * Returns string within TextField
     * @return String in TextField
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
