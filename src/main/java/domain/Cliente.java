package domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;

import java.io.Serializable;
@Getter @Setter
public abstract class Cliente implements Serializable {
    protected String nombre,apellidos;
    protected boolean hasDescuento;
    public Cliente(String nombre, String apellidos, boolean hasDescuento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.hasDescuento = hasDescuento;
    }



    public Cliente() {
        Faker f = new Faker();
        this.nombre = f.name().firstName();
        this.apellidos = f.name().lastName();
        hasDescuento = (int) (Math.random() * 2) == 1; //si se evalua a 1, sale verdadero
    }
}
