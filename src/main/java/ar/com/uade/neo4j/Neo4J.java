package ar.com.uade.neo4j;

public class Neo4J {

	public static void main(String[] args) {
		Conector database = new Conector();
		System.out.println("agregarEdificio\n" + database.agregarEdificio(5, "Prueba #2", "La viudita 679", 15));
		System.out.println("modificarEdificio\n" + database.modificarEdificio(5, "Edificio modificado", "La viudita 679", 10));
		System.out.println("consultarEdificio\n" + database.consultarEdificio(5));
		System.out.println("eliminarEdificio\n" + database.eliminarEdificio(5));
	}

}
