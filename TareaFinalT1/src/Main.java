import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SQLException {
        Controlador c = new Controlador();
        Scanner sc = new Scanner(System.in);

        //Pregutamos que desea hacer
        System.out.println("¿Que deseas hacer?\n" +
                "1. Generar XML\n" +
                "2. Obtener por Xstream\n" +
                "3. Almacenar los datos en un fichero en binario\n" +
                "4. Generar la base de datos\n" +
                "5. Hacer las modificaciones en las base de datos\n" +
                "6. Salir");

        int opcion = sc.nextInt();

        //Terminamos el programa si elige salir
        if (opcion == 6) {
            System.out.println("Programa finalizado");
            return;
        }

        do {
            switch (opcion) {
                case 1:
                    c.ejercicio1();
                    break;
                case 2:
                    c.ejercicio3();
                    break;
                case 3:
                    c.ejercicio4();
                    break;
                case 4:
                    c.ejercicio5();
                    break;
                case 5:
                    c.ejercicio6();
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
            System.out.println("¿Que deseas hacer?\n" +
                    "1. Generar XML\n" +
                    "2. Obtener por Xstream\n" +
                    "3. Almacenar los datos en un fichero en binario\n" +
                    "4. Generar la base de datos\n" +
                    "5. Hacer las modificaciones en las base de datos\n" +
                    "6. Salir");

            opcion = sc.nextInt();

        } while (opcion != 6);

        System.out.println("Programa finalizado");
    }
}
