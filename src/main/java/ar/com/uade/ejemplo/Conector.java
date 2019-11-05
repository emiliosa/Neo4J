package ar.com.uade.ejemplo;

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
		driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ));
	}
	
	public Session openSesion() {
		return driver.session();
	}
	
	public void closeSesion(Session sesion) {
		sesion.close();
	}
	
	public void getPerson() {
		
		Session sesion = this.openSesion();
		Statement query = new Statement("match(n:Person) return n");
		StatementResult record = sesion.run(query);
		List<Record> registros = record.list();
		for(Record registro : registros) {
			/*
			 * registros es una lista de nodos. 
			 * 
			 * get(0) me devuelve el primer valor del nodo
			 * 
			 * El segundo get me permite seguir por las propiedades.
			 * 
			 * */
			System.out.println(registro.get(0).get("name") + " -- " + registro.get(0).get("born"));
		}
		
		this.closeSesion(sesion);
		System.exit(0);
	}
	
	
}
