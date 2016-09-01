package ws.biotea.ld2rdf.rdf.persistence;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jena.riot.RDFFormat;

import ws.biotea.ld2rdf.util.OntologyPrefix;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import edu.stanford.smi.protege.exception.OntologyLoadException;

public class ConnectionLDModel {
	//Living Document models
	private Model ldModel;
	//private Model ldOWLModel;
	private String fileName;
	private Map<String, String> prefixes;
	
	/**
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public ConnectionLDModel() {
		List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
		lst.add(OntologyPrefix.prefixesMap_RDF());
		lst.add(AnnotationOntologyPrefix.prefixesMap_AO());
		lst.add(AnnotationOntologyPrefix.prefixesMap_OA());
		this.prefixes = OntologyPrefix.mergePrefixes(lst);
	}
	
	public ConnectionLDModel(List<Map<String, String>> lst) {
		this.prefixes = OntologyPrefix.mergePrefixes(lst);
	}
	
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws OntologyLoadException 
	 */
	public Model openJenaModel(String outName, boolean empty, RDFFormat format) throws FileNotFoundException, ClassNotFoundException, OntologyLoadException {
		this.fileName = outName;
		this.ldModel = ModelFactory.createDefaultModel();
		if (empty) {
			this.ldModel.setNsPrefixes(prefixes);
		} else {
			// use the FileManager to find the input file
			InputStream in = FileManager.get().open(outName);
			if (in == null) {
			    throw new IllegalArgumentException("File: " + outName + " not found");
			}
			if (format == RDFFormat.JSONLD) {
				this.ldModel.read(in, null, "JSON-LD");
			} else {
				this.ldModel.read(in, null, "RDF/XML-ABBREV");
			}						
		}		
		return (this.ldModel);
	} 
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws OntologyLoadException 
	 */
	public Model openJenaModel(String inName, boolean empty) throws FileNotFoundException, ClassNotFoundException, OntologyLoadException {
		return this.openJenaModel(inName, empty, RDFFormat.RDFXML_ABBREV);
	}
	
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws OntologyLoadException 
	 */
	public Model openJenaModel() throws FileNotFoundException, ClassNotFoundException, OntologyLoadException {
		return this.openJenaModel(null, true, RDFFormat.RDFXML_ABBREV);
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public void closeAndWriteJenaModel() throws FileNotFoundException {	
		this.closeAndWriteJenaModel(RDFFormat.RDFXML_ABBREV);
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public void closeAndWriteJenaModel(String outName) throws FileNotFoundException {
		this.fileName = outName;
		this.closeAndWriteJenaModel(RDFFormat.RDFXML_ABBREV);
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public void closeAndWriteJenaModel(String outName, RDFFormat format) throws FileNotFoundException {
		this.fileName = outName;
		this.closeAndWriteJenaModel(format);
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public void closeAndWriteJenaModel(RDFFormat format) throws FileNotFoundException {	
		FileOutputStream myFile = new FileOutputStream(this.fileName);
		this.ldModel.setNsPrefixes(prefixes);
		if (format == RDFFormat.JSONLD) {
			this.ldModel.write(myFile, "JSON-LD");
		} else {
			this.ldModel.write(myFile, "RDF/XML-ABBREV");
		}		
		try {
			myFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws OntologyLoadException 
	 */
	/*public JenaOWLModel openOWLModel(String uri, String fileName, boolean empty) throws FileNotFoundException, ClassNotFoundException, OntologyLoadException {
		this.fileName = fileName;
		if (empty) {
			this.ldOWLModel = ProtegeOWL.createJenaOWLModel();
			this.ldModel = this.ldOWLModel.getOntModel();
			this.ldModel.setNsPrefixes(prefixes);
		} else {
			this.ldOWLModel = ProtegeOWL.createJenaOWLModelFromURI(uri);
			this.ldModel = this.ldOWLModel.getOntModel();
		}			
		return this.ldOWLModel;
	}*/
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	/*public void closeAndWriteOWLModel() throws FileNotFoundException {
		FileOutputStream myFile = new FileOutputStream(this.fileName);
		this.ldModel = this.ldOWLModel.getOntModel();		
		this.ldModel.write(myFile, "RDF/XML-ABBREV", null); 
	}*/
	/**
	 * Returns the hypertagModel.
	 * @return the hypertagModel.
	 */
	/*public OntModel getLDJenaModel() {
		return this.ldModel;
	}*/
	/**
	 * @return the hypertagOWLModel
	 */
	public Model getLDJenaModel() {
		return ldModel;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
