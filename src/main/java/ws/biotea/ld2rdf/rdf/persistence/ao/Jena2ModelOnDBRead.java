package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.util.ResourceBundle;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;


/**
 * Continuación de Jena2ModelOnDBCreate 
 */
public class Jena2ModelOnDBRead {
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
     * Default constructor.
     * Initializes all resources required to create connections to DB.
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private Jena2ModelOnDBRead() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	this.config= ResourceBundle.getBundle("ws.biotea.ld2rdf.rdf.persistence.ao.config");
		this.database = config.getString("database");
		this.connectionClass= config.getString(this.database+".class");
		this.url=config.getString(this.database+".url");
		this.login=config.getString(this.database+".login");
		this.password=config.getString(this.database+".password");
			
		Class.forName(this.connectionClass).newInstance();
        this.dbCon = new DBConnection(this.url, this.login, this.password, "MySQL");
    }

     public static void main(String args[]) throws Exception {
    	 Jena2ModelOnDBRead jena = new Jena2ModelOnDBRead();
    	 ModelRDB modelAtDB = ModelRDB.open(jena.getDbCon(), "modelDB");
         modelAtDB.write(
                 System.out,
                 "RDF/XML",     //"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE", "N3"
                 "http://somewhere/");   //URI base
          modelAtDB.close(); //Close de connection. Use remove() for deleting
     }

	/**
	 * @return the dbCon
	 */
	public IDBConnection getDbCon() {
		return dbCon;
	}

	/**
	 * @param dbCon the dbCon to set
	 */
	public void setDbCon(IDBConnection dbCon) {
		this.dbCon = dbCon;
	}
}

