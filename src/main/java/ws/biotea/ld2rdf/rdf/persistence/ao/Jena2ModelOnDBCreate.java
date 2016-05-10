package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.util.ResourceBundle;

import com.hp.hpl.jena.db.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

/**
 * El driver se baja de donde diga Google ("com.mysql.jdbc.driver jar") --> Site
 * de MySQL El driver es un jar que hay que poner en el classpath En este
 * ejemplo, el esquema de la BD hay que hacerlo nuevo (vacío) en cada
 * invocación.
 */
public class Jena2ModelOnDBCreate {
	/**
	 * Database name
	 * */
	private String database;
	/**
	 * Connection class
	 * */
	private String connectionClass;
	/**
	 * URL DB string
	 * */
	private String url;
	/**
	 * Configuration resource bundle
	 * */
	private ResourceBundle config;
	/**
	 * DB login
	 * */
	private String login;
	/**
	 * DB password
	 * */
	private String password;
	/**
	 * Connection
	 */
	private IDBConnection dbCon;

	/**
	 * Default constructor. Initializes all resources required to create
	 * connections to DB.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private Jena2ModelOnDBCreate() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		this.config = ResourceBundle.getBundle("ws.biotea.ld2rdf.rdf.persistence.ao.config");
		this.database = config.getString("database");
		this.connectionClass = config.getString(this.database + ".class");
		this.url = config.getString(this.database + ".url");
		this.login = config.getString(this.database + ".login");
		this.password = config.getString(this.database + ".password");

		Class.forName(this.connectionClass).newInstance();
		this.dbCon = new DBConnection(this.url, this.login, this.password,
				"MySQL");
	}

	public static void main(String args[]) throws Exception {
		Jena2ModelOnDBCreate jena = new Jena2ModelOnDBCreate();
		ModelRDB modelAtDB = ModelRDB.createModel(jena.dbCon, "modelDB");
		fillingModel(modelAtDB);
		modelAtDB.write(System.out, "RDF/XML", // "RDF/XML", "RDF/XML-ABBREV",
												// "N-TRIPLE", "N3"
				"http://somewhere/"); // URI base
		modelAtDB.close(); // Close de connection. Use remove() for deleting
	}

	private static void fillingModel(Model model) {
		String jsURI = "http://somewhere/JohnSmith";
		String miURL = "http://mio/#";
		String miNS = "mio";
		Resource r = model.createResource(jsURI).addProperty(VCARD.FN,
				"John Smith");
		Property p = model.createProperty(miURL, "testOf");
		r.addProperty(p, "testing");
		model.setNsPrefix(miNS, miURL);
	}
}
