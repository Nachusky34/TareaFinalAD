import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaisesBBDD {

	public static void main(String[] args) throws SQLException, FileNotFoundException {
		PaisesBBDD p = new PaisesBBDD();
		p.generarBBDD();
	}

	public void generarBBDD() throws FileNotFoundException, SQLException {

		String url = "jdbc:sqlite:Paises.db";
		Connection conn = DriverManager.getConnection(url);
		Statement stmt2 = conn.createStatement();
		ObtenerPaisesXstream xs = new ObtenerPaisesXstream();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS Paises ("
                + "nombre TEXT PRIMARY KEY, "
                + "presidente TEXT, "
                + "pib INTEGER, "
                + "coGini REAL)";
        stmt2.executeUpdate(createTableSQL);
        System.out.println("Tabla 'Paises' creada.");


        String insertDatosPaises = "INSERT INTO Paises (nombre, presidente, pib, coGini) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertDatosPaises)) {
        	for (Pais p : xs.getListaPaises().getPaises()) {
        		stmt.setString(1, p.getNombre());
        		stmt.setString(2, p.getPresidente());
        		stmt.setLong(3, p.getPib());
        		stmt.setDouble(4, p.getCoGini());

        		stmt.executeUpdate();
        	}
        	System.out.println("Datos Insertados");
        }
	}

	public boolean guardarPais(Scanner sc) throws SQLException {
	    Connection conexion = DriverManager.getConnection("jdbc:sqlite:file:Pais;shutdown=true");
	    String sql = "INSERT INTO Pais (nombre, presidente, pib, coGini) "
	               + "VALUES (?, ?, ?, ?)";
	    PreparedStatement ps = conexion.prepareStatement(sql);

	    System.out.println("Inserte el nombre del Pais");
	    System.out.println("Inserte el presidente del Pais");
	    System.out.println("Inserte el PIB del Pais");
	    System.out.println("Inserte el coGini del Pais");

	    ps.setString(1, sc.nextLine());
	    ps.setString(2, sc.nextLine());
	    ps.setLong(3, sc.nextLong());
	    ps.setDouble(4, sc.nextDouble());

	    int filasInsertadas = ps.executeUpdate();
	    ps.close();
	    conexion.close();

	    return filasInsertadas > 0;
	}

	public void busquedaPais(Scanner sc) throws SQLException {

	    Connection conexion = DriverManager.getConnection("jdbc:sqlite:file:Pais;shutdown=true");

	    String sql = "SELECT * FROM Pais WHERE nombre = ?";
	    PreparedStatement ps = conexion.prepareStatement(sql);

	    System.out.println("Inserte el nombre del Pais");
	    String nombre = sc.nextLine();

	    ps.setString(1, nombre);
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	    	System.out.println("Nombre del Pais: " + rs.getString("nombre"));
	    	System.out.println("Presidente del Pais: " + rs.getString("presidente"));
	    	System.out.println("PIB del Pais: " + rs.getLong("pib"));
	    	System.out.println("coGini del Pais: " + rs.getDouble("coGini"));
	    } else {
	    	System.out.println("No se encontro el pais con el nombre" + nombre);
	    }

	    ps.close();
	    rs.close();
	    conexion.close();

	}

	public void modificarPaises(Scanner sc) throws SQLException {

		Connection conexion = DriverManager.getConnection("jdbc:sqlite:file:Pais;shutdown=true");

		System.out.println("Introduzca el Pais del que quieres modificar algun atributo: ");
		String nombreDelPais = sc.nextLine();

		System.out.println("---------- SELECCIONA LO QUE DESEA MODIFICAR ----------");
		System.out.println("1, Nombre");
		System.out.println("2, Presidente");
		System.out.println("3, PIB");
		System.out.println("4, Coeficiente de Gini");
		System.out.println("Seleccione Opcion: ");
		int opcion = sc.nextInt();
		//Salto de linea
		sc.nextLine();


		String campo = "";
		//Clase objeto por defento(Agrupa todas las clases)
		Object nuevoValor = "";

		switch (opcion) {
			case 1:
				System.out.println("Ingrese el nuevo nombre: ");
				campo = "nombre";
				nuevoValor = sc.nextLine();
				break;
			case 2:
				System.out.println("Ingrese el nuevo presidente");
				campo = "presidente";
				nuevoValor = sc.nextLine();
				break;
			case 3:
				System.out.println("Ingrese el nuevo PIB");
				campo = "pib";
				nuevoValor = sc.nextLong();
				break;
			case 4:
				System.out.println("Ingrese el nuevo coGini");
				campo = "coGini";
				nuevoValor = sc.nextDouble();
				break;
			default:
				System.out.println("Opcion no Válida");
				conexion.close();
				return;
		}

		String actualizarCampos = "UPDATE Pais SET" + campo + " = ? WHERE nombre = ?";
		PreparedStatement ps = conexion.prepareStatement(actualizarCampos);

		ps.setObject(1, nuevoValor);
		ps.setString(2, nombreDelPais);

		int filasActualizadas = ps.executeUpdate();
		if (filasActualizadas > 0) {
			System.out.println("Paíes Actualzado Correctamente");
		} else {
			System.out.println("No se encontró el país");
		}

		ps.close();
		conexion.close();

	}

}
