package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tomasrohr.dbdc.core.SchemaObjectName;
import org.tomasrohr.dbdc.core.impl.OraDataCatalog;
import org.tomasrohr.dbdc.dbi.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    private DCTListController controller;


    @Override
    public void start(Stage primaryStage) throws Exception{
//        controller = new DCTListController();
 //       controller.initListView();

        FXMLLoader loader = new FXMLLoader();
//        loader.setController(controller);

        Parent root = loader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 500));

        primaryStage.show();
    }


        public static void main(String[] args) {


            launch(args);
    }
}
