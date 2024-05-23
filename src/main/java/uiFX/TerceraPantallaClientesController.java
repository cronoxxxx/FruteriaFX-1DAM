package uiFX;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import service.GestionFruteria;

public class TerceraPantallaClientesController {


    public void Salir(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
}
