package ar.com.uade.neo4j;

import java.util.List;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Statement;
import org.neo4j.driver.StatementResult;

public class Conector {

	private Driver driver;

	public Conector() {
		driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "123456"));
	}

	public Session openSesion() {
		return driver.session();
	}

	public void closeSesion(Session sesion) {
		sesion.close();
	}
	
	public void getInquilinos() {

		Session sesion = this.openSesion();
		Statement query = new Statement("match(n:inquilinos) return n");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		for (Record registro : registros) {
			/*
			 * registros es una lista de nodos.
			 * 
			 * get(0) me devuelve el primer valor del nodo
			 * 
			 * El segundo get me permite seguir por las propiedades.
			 * 
			 */
			System.out.println(registro.get(0).get("DNI") + " -- " + registro.get(0).get("Nombre") + " "
					+ registro.get(0).get("Apellido"));
		}

		this.closeSesion(sesion);
		System.exit(0);
	}
	
	public String formatEdificio(List<Record> edificios) {
		String resultado = "";
		
		for (Record edificio : edificios) {
			resultado += edificio.get(0).get("Id_Edificio") + " -- " + edificio.get(0).get("Nombre") + " --- " + edificio.get(0).get("Dirección") + " --- " + edificio.get(0).get("Dptos") + "\n";
		}
		
		return resultado;
	}

	public String agregarEdificio(int id_edificio, String nombre, String direccion, int dptos) {
		Session sesion = this.openSesion();
		StatementResult result = sesion.run("CREATE (e:edificios {Id_Edificio:" + id_edificio + ",Nombre:'" + nombre + "',Dirección:'" + direccion
				+ "',Dptos:" + dptos + "}) RETURN e");
		this.closeSesion(sesion);
		
		return formatEdificio(result.list());
	}
	
	public String modificarEdificio(int id_edificio, String nombre, String direccion, int dptos) {
		Session sesion = this.openSesion();
		StatementResult result = sesion.run("MATCH (e:edificios {Id_Edificio: " + id_edificio + "}) SET e.Nombre = '" + nombre + "', e.Dirección = '" + direccion + "', e.Dptos = " + dptos + " RETURN e");
		this.closeSesion(sesion);
		
		return formatEdificio(result.list());
	}
	
	public String consultarEdificio(int id_edificio) {
		Session sesion = this.openSesion();
		StatementResult result = sesion.run("MATCH (e:edificios {Id_Edificio: " + id_edificio + "}) RETURN e");
		this.closeSesion(sesion);
		
		return formatEdificio(result.list());
	}
	
	public boolean eliminarEdificio(int id_edificio) {
		Session sesion = this.openSesion();
		StatementResult beforeDelete = sesion.run("MATCH (e:edificios {Id_Edificio: " + id_edificio + "}) RETURN e");
		sesion.run("MATCH (e:edificios {Id_Edificio: " + id_edificio + "}) DELETE e");
		StatementResult afterDelete = sesion.run("MATCH (e:edificios {Id_Edificio: " + id_edificio + "}) RETURN e");
		this.closeSesion(sesion);
		
		return beforeDelete.list().size() != afterDelete.list().size();
	}

}
