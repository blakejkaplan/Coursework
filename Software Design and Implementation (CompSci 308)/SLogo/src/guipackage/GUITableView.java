package guipackage;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.VariableDictionary;

/**
 * Creates TableView to display variables and allow editing of variables.
 */
public class GUITableView implements IGUIObject {

    private static final int TABLE_COLUMN_WIDTH = 130;
    private ResourceBundle myResources;
    private TableView<GUITableViewTableVariable> myTableView;
    private TableColumn<GUITableViewTableVariable, String> myVariableColumn;
    private TableColumn<GUITableViewTableVariable, Double> myValueColumn;
    private VariableDictionary varDict;
	private static final double PADDING_TOP = 0;
	private static final double PADDING_RIGHT = 0;
	private static final double PADDING_BOTTOM = 0;
	private static final double PADDING_LEFT = 10;

    private ObservableList<GUITableViewTableVariable> data = FXCollections.observableArrayList();

    public GUITableView(ResourceBundle r, VariableDictionary myVarDict) {
        myResources = r;
        varDict = myVarDict;
    }

    /**
     * Creates TableVariable node and populates it with cells that are editable.
     */
    @SuppressWarnings("unchecked")
	@Override
    public Node createNode() {
        myTableView = new TableView<>();
        myTableView.setEditable(true);
        
        createVariableColumn();
        
        createValueColumn();

        myTableView.setItems(data);

        myTableView.getColumns().addAll(myVariableColumn, myValueColumn);
        
        HBox tableView = new HBox();
		tableView.getChildren().addAll(myTableView);
		tableView.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
        return tableView;
    }
    /**
     * Create column to hold variable names.
     */
    public void createVariableColumn() {
        myVariableColumn =
                new TableColumn<>(myResources.getString("VariablesColumn"));
        myVariableColumn.setMinWidth(TABLE_COLUMN_WIDTH);
        myVariableColumn.setCellValueFactory(new PropertyValueFactory<GUITableViewTableVariable, String>("variableName"));
    }
    
    /**
     * Create column to hold variable values. 
     */
    public void createValueColumn() {
        Callback<TableColumn<GUITableViewTableVariable, Double>, TableCell<GUITableViewTableVariable, Double>> cellFactory =
                (event -> {
                	return new GUITableViewEditingCell();
                });
        myValueColumn =
                new TableColumn<>(myResources.getString("ValuesColumn"));
        myValueColumn.setMinWidth(TABLE_COLUMN_WIDTH);
        myValueColumn.setCellValueFactory(new PropertyValueFactory<GUITableViewTableVariable, Double>("variableValue"));
        myValueColumn.setCellFactory(cellFactory);
        myValueColumn.setOnEditCommit(event -> {
        	event.getTableView().getItems().get(event.getTablePosition().getRow()).setVariableValue(event.getNewValue());
        	varDict.makeVariable(event.getTableView().getItems().get(event.getTablePosition().getRow())
        			.getVariableName(), event.getNewValue());
        });
    }
    /**
     * Updates node when new data is available.
     */
    @Override
    public void updateNode() {
        data.clear();
        for (String s: varDict.getKeySet()) {
            data.add(new GUITableViewTableVariable(s, varDict.getNodeFor(s)));
        }
    }
}
