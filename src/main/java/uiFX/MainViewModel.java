package uiFX;

import dao.DaoFruteriaImplementacion;
import domain.Fruta;
import service.GestionFruteria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {
    private final GestionFruteria gestionFruteria;
    private final ObservableList<Fruta> frutas;

    private MainViewModel() {
        gestionFruteria = new GestionFruteria(new DaoFruteriaImplementacion());
        frutas = FXCollections.observableArrayList(gestionFruteria.mostrarInformacion(true));
        gestionFruteria.escribirFichero();
    }

    public MainViewModel(GestionFruteria gestionFruteria) {
        this.gestionFruteria = gestionFruteria;
        frutas = FXCollections.observableArrayList(gestionFruteria.mostrarInformacion(true));
        gestionFruteria.escribirFichero();

    }
    public ObservableList<Fruta> obtenerFrutas() {
        return frutas;
    }

    public GestionFruteria getServicioFruteria() { return gestionFruteria; }
}
