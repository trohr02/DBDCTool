package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomasrohr.dbdc.core.SchemaObjectName;
import org.tomasrohr.dbdc.core.impl.OraDataCatalog;
import org.tomasrohr.dbdc.dbi.ConnectionManager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DCTListController implements Initializable {
    public static final Logger logger = LoggerFactory.getLogger(DCTListController.class.getName());

    @FXML
    public ListView<SchemaObjectName> dcTableListView;

    private OraDataCatalog dc;

    public DCTListController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDataCatalog();
        initListView();
    }


    private void initDataCatalog() {
        ConnectionManager.listDrivers();


        try(Connection c = ConnectionManager.getConnection("system","oracle")) {
            logger.info("Connected");

            dc = new OraDataCatalog(c);
            dc.loadTables();
            for( SchemaObjectName o: dc.getTables().keySet() ) {
                System.out.println(o.toString());
            }
        } catch (SQLException e) {
            logger.error("Connection failed");
            e.printStackTrace();
        } catch ( IOException ioe ) {

        }

    }



    public void initListView() {

        ObservableList<SchemaObjectName> oList = FXCollections.observableArrayList();
        oList.add(new GoUpItem());
        oList.addAll(dc.getTables().keySet());
        //FXCollections.observableArrayList(dc.getTables().keySet())
        //System.out.println(dc.getTables().toString());
        dcTableListView.setItems( oList );

/*
        dcTableListView.setItems(FXCollections.observableArrayList(
                "chocolate", "salmon", "gold", "coral", "darkorchid",
                "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
                "blueviolet", "brown"));
*/


        dcTableListView.setCellFactory( new  Callback<ListView<SchemaObjectName>,ListCell<SchemaObjectName>>() {
            @Override
            public ListCell<SchemaObjectName> call(ListView<SchemaObjectName> p) {
                return new SchemaObjectNameCell();
            }
        } );

    }
}

class GoUpItem extends SchemaObjectName {

    public GoUpItem() {
        super("","");
    }

    @Override
    public String toString() {
        return "..";
    }
}
