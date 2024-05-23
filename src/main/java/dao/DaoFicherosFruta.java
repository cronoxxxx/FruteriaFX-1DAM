package dao;




import common.AgregarProvinciasException;
import common.Constantes;
import common.FechaInvalidaException;
import common.precioVentaExcepcion;
import domain.Fruta;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaoFicherosFruta {


    private static String FICHERO = "data/Fichero", FICHEROBINARIO = "data/Clientes.bin";

    public static void crearFicheros() throws IOException {
        File fichero1 = new File(FICHERO);
        File fichero2 = new File(FICHEROBINARIO);
        String ruta = "data/";
        String nombreArchivo = "facturas.json";
        String rutaCompleta = ruta + nombreArchivo;

        File fichero3 = new File(rutaCompleta);
        if (!fichero3.exists()) {
            File directorio = new File(ruta);
            directorio.mkdirs();
            fichero3.createNewFile();
        }
        if (!fichero1.exists()) {
            fichero1.createNewFile();
        }
        if (!fichero2.exists())
            fichero2.createNewFile();
            

    }

    public static boolean escribirFichero(List<Fruta> frutas) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(FICHERO);
        for (int i = 0; i < frutas.size(); i++) {
            pw.println(frutas.get(i).toStringFile());
        }
        pw.close();
        return true;
    }


    public static List<Fruta> leerFichero() throws FileNotFoundException {
        List<Fruta> frutas = null;
        File fichero = new File(FICHERO);
        try (Scanner sc = new Scanner(fichero)) {
            frutas = new ArrayList<>();
            while ((sc.hasNextLine())) {
                String cadena = sc.nextLine();
                String[] partes = cadena.split(";");
                if (partes.length >=5) { // Verificar que hay 5 partes en la línea
                    String nombre = partes[0];
                    String procedencia = partes[1];
                    int numeroKilos = Integer.parseInt(partes[2]);
                    double precioCostePorKilo = Double.parseDouble(partes[3]);
                    double precioVentaPorKilo = Double.parseDouble(partes[4]);
                    LocalDate fechaCaducidad = LocalDate.parse(partes[5]);
                    int cantidadesVendidas = Integer.parseInt(partes[6]);
                    Fruta fruta = new Fruta(nombre, procedencia, numeroKilos, precioCostePorKilo, precioVentaPorKilo,fechaCaducidad,cantidadesVendidas);
                    frutas.add(fruta);
                } else {
                    System.out.println(Constantes.FORMATO_INCORRECTO_EN_LA_LINEA + cadena);
                    // Manejo de líneas con formato incorrecto si es necesario
                }
            }
        } catch (IOException e) {
            // Manejo de errores de lectura
            e.printStackTrace();
        } catch (precioVentaExcepcion | FechaInvalidaException e) {
            System.out.println(e.getMessage());
        } catch (AgregarProvinciasException e) {
            throw new RuntimeException(e);
        }
        return frutas;
    }

    public static Mostrador  leerFicheroBinario()  {
        Mostrador auxiliar = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FICHEROBINARIO))) {
            auxiliar = (Mostrador) is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaoFicherosFruta.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
        return auxiliar;
    }



    public static boolean escribirFicheroBinario(Mostrador mostrador) {
        boolean escrito = false;
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FICHEROBINARIO))) {
            os.writeObject(mostrador);
            escrito = true;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DaoFicherosFruta.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
        return escrito;
    }


    public static Database  leerFicheroBinarioData()  {
        Database auxiliar = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FICHEROBINARIO))) {
            auxiliar = (Database) is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaoFicherosFruta.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
        return auxiliar;
    }



    public static boolean escribirFicheroBinarioData(Database database) {
        boolean escrito = false;
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FICHEROBINARIO))) {
            os.writeObject(database);
            escrito = true;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DaoFicherosFruta.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
        }
        return escrito;
    }



}
