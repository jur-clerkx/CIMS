/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Company;

/**
 *
 * @author Jense
 */
import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Jense
 */
public class Company extends Application {

    private TreeView<String> treeView;
    private TableView<Employee> table = new TableView<>();
    public static ObservableList<Employee> employees = FXCollections.observableArrayList();
    private TreeItem<String> rootNode = new TreeItem<>("Human Resources");
    private final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        employees.add(new Employee("Ethan Williams", "Sales Department"));
        employees.add(new Employee("Emma Jones", "Sales Department"));
        employees.add(new Employee("Michael Brown", "Sales Department"));
        employees.add(new Employee("Anna Black", "Sales Department"));
        employees.add(new Employee("Rodger York", "Sales Department"));
        employees.add(new Employee("Susan Collins", "Sales Department"));
        employees.add(new Employee("Mike Graham", "IT Support"));
        employees.add(new Employee("Judy Mayer", "IT Support"));
        employees.add(new Employee("Gregory Smith", "IT Support"));
        employees.add(new Employee("Jacob Smith", "Accounts Department"));
        employees.add(new Employee("Isabella Johnson", "Accounts Department"));

        buildTree();

        Scene scene = new Scene(new Group());
        stage.setTitle("Employee Database");
        stage.setWidth(610);
        stage.setHeight(550);

        table.setEditable(true);

        TableColumn tcName = new TableColumn("Name");
        tcName.setMinWidth(100);
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(
                new EventHandler<CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setName(t.getNewValue());
                        buildTree();
                    }
                }
        );

        TableColumn departmentcol = new TableColumn("Department");
        departmentcol.setMinWidth(100);
        departmentcol.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentcol.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentcol.setOnEditCommit(
                new EventHandler<CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setDepartment(t.getNewValue());
                        buildTree();
                    }
                }
        );

        TableColumn imageCol = new TableColumn("Image");
        imageCol.setMinWidth(100);
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageCol.setCellFactory(param -> {
            ImageView imageView = new ImageView();
            TableCell<Employee, Image> cell = new TableCell<Employee, Image>() {
                @Override
                protected void updateItem(Image item, boolean empty) {
                    if (item == null) {
                        return;
                    }
                    HBox box = new HBox();
                    box.setSpacing(10);

                    VBox vBox = new VBox();

                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    imageView.setImage(item);

                    box.getChildren().addAll(imageView, vBox);
                    setGraphic(box);
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() < 2) {
                        return;
                    }

                    FileChooser file = new FileChooser();
                    file.setTitle("Selecteer een afbeelding.");

                    File f = file.showOpenDialog(stage);
                    if (f != null) {
                        Image image = new Image(f.toURI().toString());
                        imageView.setImage(image);
                        ((Employee) cell.getTableRow().getItem()).setImage(image);
                    }
                }
            });

            return cell;
        });

        table.setItems(employees);
        table.getColumns().addAll(tcName, departmentcol, imageCol);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(tcName.getPrefWidth());
        final TextField addDepartment = new TextField();
        addDepartment.setMaxWidth(imageCol.getPrefWidth());
        addDepartment.setPromptText("Department");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                employees.add(new Employee(
                        addName.getText(),
                        addDepartment.getText()));
                addName.clear();
                addDepartment.clear();
                buildTree();
            }
        });

        hb.getChildren().addAll(addName, addDepartment, addButton);
        hb.setSpacing(3);

        treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<String> p) -> new TextfieldTreeCellImplemetation());

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(treeView, 1, 1);
        grid.add(table, 2, 1);
        grid.add(hb, 2, 2);

        vbox.getChildren().addAll(grid);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void buildTree() {
        rootNode = new TreeItem<>("Human Resources");
        rootNode.setExpanded(true);
        for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.toString());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())) {
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem depNode = new TreeItem(employee.getDepartment());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                treeView.setRoot(rootNode);
            }
        });
    }
}
