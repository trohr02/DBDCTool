package sample;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.tomasrohr.dbdc.core.SchemaObjectName;

public class SchemaObjectNameCell extends ListCell<SchemaObjectName> {


    public SchemaObjectNameCell() {
        super();
    }


    @Override
    protected void updateItem(SchemaObjectName item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);

        // format the number as if it were a monetary value using the
        // formatting relevant to the current locale. This would format
        // 43.68 as "$43.68", and -23.67 as "-$23.67"
        setText(item == null ? "" : item.toString() );

    }


    public void setOnEnter(EventHandler<KeyEvent> handler) {
        this.setOnKeyPressed(handler);
    }

/*
            this.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if ( KeyCode.ENTER.equals(event.getCode()) ) {
                this
            }
        }
    });
*/

    protected EventHandler<? super KeyEvent> on



}
