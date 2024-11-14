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
		p.getPaises();
		p.modificarPib();
		p.getPaises();
		p.modificarGini();
		p.getPaises();
	}

	public void getPaises() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");
		PreparedStatement sentencia;
		ResultSet resultado;

		sentencia = conn.prepareStatement("SELECT * FROM Paises");
		System.out.println("BBDD:");
		resultado = sentencia.executeQuery();

		boolean hayDatos = false;
		while (resultado.next()) {
			System.out.println("NOMBRE: " + resultado.getString("nombre") + "  - PRESIDENTE: "
					+ resultado.getString("presidente") + "  - PIB: " + resultado.getLong("pib")
					+ "  - COEFICIENTE DE GINI: " + resultado.getDouble("coGini"));
			hayDatos = true;
		}

		if (!hayDatos) {
			System.out.println("La tabla PAISES está vacía.");
		}
		System.out.println("-------------------------------------------------------------------------------");

		conn.close();

	}

	public void generarBBDD() throws FileNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");
		Statement stmt2 = conn.createStatement();
		ObtenerPaisesXstream xs = new ObtenerPaisesXstream();
		PreparedStatement delete = conn.prepareStatement("DROP TABLE Paises");
		delete.executeUpdate();

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
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");
	    String sql = "INSERT INTO Pais (nombre, presidente, pib, coGini) "
	               + "VALUES (?, ?, ?, ?)";
	    PreparedStatement ps = conn.prepareStatement(sql);

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
	    conn.close();

	    return filasInsertadas > 0;
	}

	public void busquedaPais(Scanner sc) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");

	    String sql = "SELECT * FROM Pais WHERE nombre = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);

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
	    conn.close();

	}

	public void modificarPaises(Scanner sc) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");

		System.out.println("---------- SELECCIONA LO QUE DESEA MODIFICAR ----------");
		System.out.println("1, Nombre");
		System.out.println("2, Presidente");
		System.out.println("3, PIB");
		System.out.println("4, Coeficiente de Gini");
		System.out.println("Seleccione Opcion: ");
		int opcion = sc.nextInt();
		//Salto de linea
		sc.nextLine();

		String nombre = null;
		Object nuevoValor = null;
		String campo = null;

		System.out.println("Cuantos paises desea modificar: ");

		for (int i = 0; i < sc.nextInt(); i++) {
			System.out.println("Que pais deseas modificar? + \n" +
					"1: Belice\n" +
					"2: El Salvador\n" +
					"3: Guatemala\n" +
					"4: Honduras\n" +
					"5: Nicaragua\n" +
					"6: México\n" +
					"7: Panamá\n" +
					"8: Costa Rica");
			int pais = sc.nextInt();

			switch (pais) {
				case 1:
					nombre = "Belice";
					break;
				case 2:
					nombre = "El Salvador";
					break;
				case 3:
					nombre = "Guatemala";
					break;
				case 4:
					nombre = "Honduras";
					break;
				case 5:
					nombre = "Nicaragua";
					break;
				case 6:
					nombre = "México";
					break;
				case 7:
					nombre = "Panamá";
					break;
				case 8:
					nombre = "Costa Rica";
					break;
				default:
					System.out.println("Opcion no Válida");
					conn.close();
					return;
			}

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
					conn.close();
					return;
			}
		}

		String actualizarCampos = "UPDATE Pais SET" + campo + " = ? WHERE nombre = ?";
		PreparedStatement ps = conn.prepareStatement(actualizarCampos);

		ps.setObject(1, nuevoValor);
		ps.setString(2, nombre);

		int filasActualizadas = ps.executeUpdate();
		if (filasActualizadas > 0) {
			System.out.println("Paíes Actualzado Correctamente");
		} else {
			System.out.println("No se encontró el país");
		}

		ps.close();
		conn.close();

	}

	public void modificarPib() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");

		PreparedStatement aumentarPib = conn.prepareStatement("UPDATE Paises SET pib = pib + 10000000 ");
		aumentarPib.executeUpdate();
	}

	public void modificarGini() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:file:Paises.db");
		String mod = "UPDATE Paises SET coGini = coGini * 2/3 WHERE nombre = ? OR nombre = ? OR nombre = ?";

		try(PreparedStatement ps = conn.prepareStatement(mod)) {
			ps.setString(1,"El Salvador");
			ps.setString(2, "Honduras");
			ps.setString(3, "México");

			ps.executeUpdate();
		}
	}
}
