package uiFX;
//Para pasar información entre ventanas ver el siguiente enlace
//https://medium.com/@devtony101/tutorial-javafx-cbe534aa3a98


import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;
import org.kordamp.bootstrapfx.BootstrapFX;


import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalController implements Initializable {
    @FXML
    private MenuBar menuPrincipal;
    @FXML
    private BorderPane borderPane;
    @FXML
    public MenuItem pantallaPrimera;
    @FXML
    public MenuItem pantallaSegunda;
    @FXML
    public MenuItem pantallaTercera;

    @FXML
    public MFXPasswordField pwdField; //para contraseñas

    private static final String pass = "messi";


    @FXML
    public void menuClick(ActionEvent actionEvent) {
        Pane view;
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "pantallaPrimera" -> {

                view = new FxmlLoader().getView("Screen1.fxml");


                borderPane.setCenter(view);
                menuPrincipal.setVisible(false);

                //view.setUserData(true);
            }
            case "pantallaSegunda" -> {
                view = new FxmlLoader().getView("Screen2.fxml");
                view.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                borderPane.setCenter(view);
                menuPrincipal.setVisible(true);
            }
            case "pantallaTercera" -> {
                view = new FxmlLoader().getView("Screen3.fxml");
                borderPane.setCenter(view);
                menuPrincipal.setVisible(false);
            }
        }

    }

    public PrincipalController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuPrincipal.setVisible(false);
    }

    public void Aceptar(ActionEvent actionEvent) {
        if (pwdField.getText().equals(pass)) {
            menuPrincipal.setVisible(true);
        } else {
            pwdField.setText("");
            alertaPassword();
        }
    }

public void alertaPassword() {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error");
alert.setHeaderText("Contraseña Incorrecta");
alert.setContentText("Introduce la contraseña correcta");
alert.show();
}
}
