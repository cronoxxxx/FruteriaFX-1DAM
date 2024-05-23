package uiFX;

import common.AgregarProvinciasException;
import common.FechaInvalidaException;
import common.precioVentaExcepcion;
import dao.DaoFicherosFruta;
import dao.DaoFruteriaImplementacion;
import domain.Fruta;

import io.github.palexdev.materialfx.controls.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import service.GestionFruteria;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SegundaPantallaClientesController implements Initializable {

    private final MainViewModel viewModel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MFXButton botonAdd;
    @FXML
    private MFXButton botonDelete;
    @FXML
    private MFXButton botonUpdate;
    @FXML
    private TableView<Fruta> tablaFrutas;
    @FXML
    private TableColumn<Fruta, String> columna1;
    @FXML
    private TableColumn<Fruta, String> columna2;
    @FXML
    private TableColumn<Fruta, String> columna3;
    @FXML
    private TableColumn<Fruta, String> columna4;
    @FXML
    private TableColumn<Fruta, String> columna5;
    @FXML
    private TableColumn<Fruta, String> columna6;
    @FXML
    private TableColumn<Fruta, String> columna7;

    @FXML
    private MFXTextField nombre;

    @FXML
    private MFXTextField procedencia;
    @FXML
    private MFXTextField numeroKilos;
    @FXML
    private MFXTextField precioVenta;
    @FXML
    private MFXTextField precioCoste;
    @FXML
    private MFXDatePicker caducidad;
    @FXML
    private MFXTextField numeroVentas;

    @FXML
    private Label label;
    @FXML
    private MFXToggleButton toggleidioma;

    @FXML
    private MFXToggleButton modooscuro;


    public SegundaPantallaClientesController() {
        viewModel = new MainViewModel(new GestionFruteria(new DaoFruteriaImplementacion()));
        try {
            DaoFicherosFruta.escribirFichero(viewModel.getServicioFruteria().getFrutas());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaFrutas.setItems(viewModel.obtenerFrutas());
        //mapeo de los atributos a las columnas
        columna1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna2.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
        columna3.setCellValueFactory(new PropertyValueFactory<>("numeroKilos"));
        columna4.setCellValueFactory(new PropertyValueFactory<>("precioVentaPorKilo"));
        columna5.setCellValueFactory(new PropertyValueFactory<>("precioCostePorKilo"));
        columna6.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        columna7.setCellValueFactory(new PropertyValueFactory<>("cuantasVecesSeVendio"));
        //comboBox.getItems().addAll(resourceBundle.getString("combo1"), resourceBundle.getString("combo2"), resourceBundle.getString("combo3"), resourceBundle.getString("combo4"));
        //si queremos que al seleccionar un elemento de la tabla se rellenen los textField hay que añadir un listener a la tabla para que
        //ejecute el método onEdit cada vez que ocurra..
        tablaFrutas.setOnMouseClicked((MouseEvent event) -> onEdit());
    }

    public void onEdit() {
        // check si se ha seleccionado un elemento y actualiza los textField con los valores de los atributos del elemento seleccionado
        // Con esto no haría falta la imagen de ayuda puesto que el usuario no tendría que introducirlo en los textField
        if (tablaFrutas.getSelectionModel().getSelectedItem() != null) {
            Fruta selectedFruta = tablaFrutas.getSelectionModel().getSelectedItem();
            nombre.setText(selectedFruta.getNombre());
            procedencia.setText(selectedFruta.getProcedencia());
            numeroKilos.setText(String.valueOf(selectedFruta.getNumeroKilos()));
            precioCoste.setText(String.valueOf(selectedFruta.getPrecioCostePorKilo()));
            precioVenta.setText(String.valueOf(selectedFruta.getPrecioVentaPorKilo()));

            // Obtén el valor de caducidad como String
            String caducidadString = selectedFruta.getFechaCaducidad().toString();
            // Convierte el valor a un objeto LocalDate
            LocalDate caducidadDate = LocalDate.parse(caducidadString);
            // Establece el valor en el MFXDatePicker
            caducidad.setValue(caducidadDate);
            numeroVentas.setText(String.valueOf(selectedFruta.getCuantasVecesSeVendio()));
        }
    }
    @FXML
    private void cambioIdioma() {
        ResourceBundle bundle;
        if (toggleidioma.isSelected()) {
            bundle = ResourceBundle.getBundle("textosFX", Locale.ENGLISH);
        } else {
            bundle = ResourceBundle.getBundle("textosFX", Locale.getDefault());
        }
        label.setText(bundle.getString("titulo"));
        columna1.setText(bundle.getString("columnaNombre"));
        columna2.setText(bundle.getString("columnaProcedencia"));
        columna3.setText(bundle.getString("columnaNKilos"));
        columna4.setText(bundle.getString("columnaVentaKilo"));
        columna5.setText(bundle.getString("columnaCosteKilo"));
        columna6.setText(bundle.getString("columnaFecha"));
        columna7.setText(bundle.getString("columnaNVentas"));

        botonAdd.setText(bundle.getString("botonAdd"));
        botonDelete.setText(bundle.getString("botonDelete"));
        botonUpdate.setText(bundle.getString("botonUpdate"));
        nombre.setPromptText(bundle.getString("columnaNombre"));
        procedencia.setPromptText(bundle.getString("columnaProcedencia"));
        numeroKilos.setPromptText(bundle.getString("columnaNKilos"));
        precioVenta.setPromptText(bundle.getString("columnaVentaKilo"));
        precioCoste.setPromptText(bundle.getString("columnaCosteKilo"));
        caducidad.setPromptText(bundle.getString("columnaFecha"));
        numeroVentas.setPromptText(bundle.getString("columnaNVentas"));

        toggleidioma.setText(bundle.getString("idioma"));
        modooscuro.setText(bundle.getString("modooscuro"));
        //comboBox.getItems().clear();
        //comboBox.getItems().addAll(bundle.getString("combo1"), bundle.getString("combo2"), bundle.getString("combo3"), bundle.getString("combo4"));
    }

    @FXML
    private void modoOscuro() {
        if (modooscuro.isSelected()) {
            toggleidioma.setTextFill(Color.WHITE);
            modooscuro.setTextFill(Color.WHITE);
            anchorPane.setStyle("-fx-background-color: #383737;");
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-background-color: #000000");
            botonAdd.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
            botonDelete.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
            botonUpdate.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
        } else {
            toggleidioma.setTextFill(Color.BLACK);
            modooscuro.setTextFill(Color.BLACK);
            anchorPane.setStyle("-fx-background-color: #e6e1ec;");
            label.setTextFill(Color.BLACK);
            label.setStyle("-fx-background-color: #ffffff");
            botonAdd.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
            botonDelete.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
            botonUpdate.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
        }
    }

    @FXML
    private void addFruta() throws precioVentaExcepcion, FechaInvalidaException, AgregarProvinciasException {
        if (nombre.getText().isEmpty() || procedencia.getText().isEmpty() || numeroKilos.getText().isEmpty() || precioCoste.getText().isEmpty() || precioVenta.getText().isEmpty() || caducidad.getText().isEmpty() || numeroVentas.getText().isEmpty()) {
            alertaErrorAddAnimal();
        } else {
           Fruta fruta = new Fruta(nombre.getText(),procedencia.getText(),Integer.parseInt(numeroKilos.getText()),Double.parseDouble(precioCoste.getText()),Double.parseDouble(precioVenta.getText()),caducidad.getValue(),Integer.parseInt(numeroVentas.getText()));
            if (viewModel.getServicioFruteria().darAltaFruta(fruta)) {

                tablaFrutas.getItems().add(fruta);
                alertaOKAddAnimal();
                limpiarCampos();

            } else {
                alertaErrorAddAnimal();
            }
        }
        viewModel.getServicioFruteria().escribirFichero();
    }

    @FXML
    private void deleteFruta() {
        Fruta fruta = tablaFrutas.getSelectionModel().getSelectedItem();
        if (fruta != null && viewModel.getServicioFruteria().removeFruta(fruta)) {

            alertaConfirmationDeleteAnimal(fruta);
            alertaOkDeleteAnimal();
            limpiarCampos();
        } else {
            alertaErrorDeleteAnimal();
        }
        viewModel.getServicioFruteria().escribirFichero();
    }

    @FXML
    private void updateFruta() throws precioVentaExcepcion, FechaInvalidaException, AgregarProvinciasException {
        if (nombre.getText() == null || procedencia.getText() == null || numeroKilos.getText() == null || precioCoste.getText() == null || precioVenta.getText() == null || caducidad.getText() == null || numeroVentas.getText() == null) {
            alertaErrorUpdateAnimal();
        } else {
            Fruta fruta1 = new Fruta(nombre.getText(),procedencia.getText(),Integer.parseInt(numeroKilos.getText()),Double.parseDouble(precioCoste.getText()),Double.parseDouble(precioVenta.getText()),caducidad.getValue(),Integer.parseInt(numeroVentas.getText()));
            Fruta fruta2 = tablaFrutas.getSelectionModel().getSelectedItem();
            if (viewModel.getServicioFruteria().updateFruta(fruta1, fruta2)) {

                tablaFrutas.getItems().remove(fruta2);
                tablaFrutas.getItems().add(fruta1);
                viewModel.getServicioFruteria().escribirFichero();
                alertaOKUpdateAnimal();
                limpiarCampos();
                tablaFrutas.refresh();
            } else {
                alertaErrorUpdateAnimal();
            }
        }

    }

    private void limpiarCampos() {
        nombre.clear();
        procedencia.clear();
        numeroKilos.clear();
        precioVenta.clear();
        precioCoste.clear();
        caducidad.clear(); //o set value null
        numeroVentas.clear();
    }

    @FXML
    private void ayuda() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Ayuda");
        a.setHeaderText("Ayuda");
        a.setContentText("Selecciona la fruta a actualizar en la tabla e introduce los nuevos datos");
        a.show();
    }

    @FXML
    private void alertaErrorAddAnimal() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al añadir la fruta");
        alert.setContentText("No se ha podido añadir la fruta");
        alert.show();
    }

    private void alertaOKAddAnimal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fruta añadida correctamente");
        alert.setHeaderText("Fruta añadida correctamente");
        alert.setContentText("Se ha añadida correctamente");
        alert.show();

    }

    private void alertaOKUpdateAnimal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fruta actualizada con éxito");
        alert.setHeaderText("Fruta actualizada con éxito");
        alert.setContentText("Se ha actualizada correctamente");
        alert.show();
    }

    @FXML
    private void alertaErrorUpdateAnimal() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al actualizar fruta");
        alert.setContentText("Problemas al actualizar la fruta");
        alert.show();
    }

    private void alertaConfirmationDeleteAnimal(Fruta fruta) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Diálogo confirmación");
        alert.setContentText("Confirma el borrado de " + fruta + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tablaFrutas.getItems().remove(fruta);
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Animal eliminada con éxito");
            alert2.setHeaderText("Animal eliminada con éxito");
            alert2.setContentText("Se ha eliminado correctamente");
            alert2.show();
        }

    }

    private void alertaOkDeleteAnimal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Animal eliminada con éxito");
        alert.setHeaderText("Animal eliminada con éxito");
        alert.setContentText("Se ha eliminado correctamente");
        alert.show();
    }

    @FXML
    private void alertaErrorDeleteAnimal() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al eliminar el animal");
        alert.setContentText("No se ha podido eliminar el animal");
        alert.show();
    }

}