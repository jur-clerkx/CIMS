/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Jense
 */
public class CIMS_Server extends Application {

    private Server server;
    private Label serverStatus;
    private ListView<String> OutputView;
    public static ObservableList<String> Output;

    @Override
    public void start(Stage primaryStage) throws Exception {
        server = new Server(1250);
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Button to start server
        Button startServer = new Button();
        startServer.setText("Start Server");
        startServer.setOnAction((ActionEvent event) -> {
            StartServer(event);
        });

        // Button to stop server
        Button stopServer = new Button();
        stopServer.setText("Stop Server");
        stopServer.setOnAction((ActionEvent event) -> {
            StopServer(event);
        });

        // Button to clear log
        Button ClearLog = new Button();
        ClearLog.setText("Clear the console");
        ClearLog.setOnAction((ActionEvent event) -> {
            ClearLog(event);
        });

        Output = FXCollections.observableArrayList();
        OutputView = new ListView<>(Output);
        grid.add(OutputView, 2, 1);

        serverStatus = new Label("Server status = Offline");
        serverStatus.setTextFill(Color.RED);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.getChildren().addAll(startServer, stopServer, serverStatus);
        grid.add(vbButtons, 1, 1);

        Group root = new Group();
        Scene scene = new Scene(root, 450, 500);
        root.getChildren().add(grid);

        primaryStage.setTitle("CIMS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void StartServer(ActionEvent event) {
        if (!server.getSearching()) {
            server.start();
            serverStatus.setText("Server status = Online!");
            serverStatus.setTextFill(Color.GREEN);
            Output.add("Server Started");
        }
    }

    private void StopServer(ActionEvent event) {
        if (server.getSearching()) {
            server.stop();
            serverStatus.setText("Server status = Offline!");
            serverStatus.setTextFill(Color.RED);
            Output.add("Server Stopped");
        }
    }

    private void ClearLog(ActionEvent event) {
        Output.clear();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
