package ui;

import lombok.extern.log4j.Log4j2;
import common.Constantes;
import service.GestionFruteria;
import service.GestionMostrador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Log4j2
public class MenuPrograma {
    public static void action() {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        GestionMostrador gestionMostrador = new GestionMostrador();
        GestionFruteria gestionFruteria = new GestionFruteria();
        int var;
        boolean continuar = true;
        try {
            do {
                System.out.println(Constantes.MENU_BIENVENIDA);
                try {
                    var = Integer.parseInt(entradaReader.readLine());
                    switch (var) {
                        case 1-> {
                            InterfazMostrador.getInterfazMostrador(gestionMostrador);
                            continuar = false; //salida del programa
                        }
                        case 2-> {
                            InterfazFruteria.getInterfazFruteria(gestionFruteria);
                            continuar = false;
                        }
                        case 3->{
                            System.out.println(Constantes.VUELVA_PRONTO);
                            continuar = false;
                        }
                        default-> System.out.println(Constantes.OPCION_NO_VALIDA);

                    }
                } catch (NumberFormatException e) {
                    log.error(e.getMessage(),e);
                }
            } while (continuar);
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }














}
