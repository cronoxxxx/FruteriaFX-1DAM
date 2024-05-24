package dao;

import domain.Cliente;
import domain.ClienteFisico;
import domain.ClienteOnline;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@Getter@Setter
public class Database implements Serializable
{
    private int iterador = 0;
    private  double beneficios = 0;
    private int fila = 0;
    private final Map<Integer, Cliente> clientesEsperaCompra;
    public Database() {
        clientesEsperaCompra = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            int randoMizer = (int) (Math.random() * 2);
            if (randoMizer == 1) {
                clientesEsperaCompra.put(iterador++, new ClienteFisico());
            } else {
                clientesEsperaCompra.put(iterador++, new ClienteOnline());
            }
        }
    }

}
