import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.thoughtworks.xstream.XStream;



public class PaisesBBDD {

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		
		String url = "jdbc:sqlite:Paises.db";
		Connection conn = DriverManager.getConnection(url);
		Statement stmt2 = conn.createStatement();
		
		FileInputStream fil1 = new FileInputStream("paises.xml");

        XStream xstream = new XStream();
        Class[] clases = {ListaPaises.class, Pais.class};
        xstream.allowTypes(clases);
        xstream.alias("Paises", ListaPaises.class);
        xstream.alias("Pais", Pais.class);
        xstream.addImplicitCollection(ListaPaises.class, "Paises");

        ListaPaises Paises = (ListaPaises) xstream.fromXML(fil1);
        //System.out.println(Paises.getPaises().toString());

        
        //Paises.Imprimir();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Paises ("
                + "nombre TEXT PRIMARY KEY, "
                + "presidente TEXT, "
                + "pib INTEGER, "
                + "coGini REAL)";
        stmt2.executeUpdate(createTableSQL);
        System.out.println("Tabla 'Paises' creada.");
        
        
        String insertDatosPaises = "INSERT INTO Paises (nombre, presidente, pib, coGini) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(insertDatosPaises)) {
        	for (Pais p : Paises.getPaises()) {
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
	    String nombre = sc.nextLine();
	    System.out.println("Inserte el presidente del Pais");
	    String presidente = sc.nextLine();
	    System.out.println("Inserte el PIB del Pais");
	    Long pib = sc.nextLong();
	    System.out.println("Inserte el coGini del Pais");
	    Double coGini = sc.nextDouble();
	    		
	    ps.setString(1, nombre);
	    ps.setString(2, presidente);
	    ps.setLong(3, pib);
	    ps.setDouble(4, coGini);
	    		
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
			System.out.println("Paíss Actualzado Correctamente");
		} else {
			System.out.println("No se encontró el país");
		}
		
		ps.close();
		conexion.close();
		
		
		
	}

}
