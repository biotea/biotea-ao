package ws.biotea.ld2rdf.rdf.persistence;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import ws.biotea.ld2rdf.exception.RDFModelIOException;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;
import ws.biotea.ld2rdf.util.mapping.DatatypeProperty;
import ws.biotea.ld2rdf.util.mapping.MappingConfig;

public abstract class AnnotationDAO {
	protected int nodeCounter = 0;
	/**
	 * Inserts a new annotation.
	 * @param OpenAnnotation OpenAnnotation to be updated.
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	protected abstract URI insertAnnotation(String base, String baseURL, AnnotationE annot, String id, Model modelOut
			, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException;
	/**
	 * Inserts a new annotation.
	 * @param OpenAnnotation OpenAnnotation to be updated.
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	protected abstract URI insertAnnotation(String base, String baseURL, AnnotationE annot, String id, String fileOut
			, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException;
	/**
	 * Inserts a list of annotations.
	 * @param baseURL
	 * @param list
	 * @param fileOut
	 * @param empty
	 * @throws Exception
	 */
	public abstract List<AnnotationE> insertAnnotations(String base, String baseURL, List<AnnotationE> list
			, Model modelOut, boolean blankNode) throws RDFModelIOException;
	/**
	 * Inserts a list of annotations.
	 * @param baseURL
	 * @param list
	 * @param fileOut
	 * @param empty
	 * @throws Exception
	 */
	public abstract List<AnnotationE> insertAnnotations(String base, String baseURL, List<AnnotationE> list
			, String fileOut, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException;
	/**
	 * Updates an annotation.
	 * @param OpenAnnotation OpenAnnotation to be updated.
	 * @throws Exception
	 */
	protected abstract URI updateAnnotation(String base, String baseURL, AnnotationE annot, String uri
			, String fileOut, RDFFormat format, boolean empty) throws Exception;
	/**
	 * Updates an annotation.
	 * @param OpenAnnotation OpenAnnotation to be updated.
	 * @throws Exception
	 */
	protected abstract URI updateAnnotation(String base, String baseURL, AnnotationE annot, String uri
			, Model modelOut) throws Exception;
	/**
	 * Deletes an annotation given its id.
	 * @param id OpenAnnotation id to be deleted.
	 * @throws Exception
	 */
	protected abstract void deleteAnnotation(String baseURL, String id, String uri, String fileOut, RDFFormat format
			, boolean empty) throws UnsupportedOperationException;
	/**
	 * Deletes an annotation given its id.
	 * @param id OpenAnnotation id to be deleted.
	 * @throws Exception
	 */
	protected abstract void deleteAnnotation(String baseURL, String id, String uri, Model modelOut) throws UnsupportedOperationException;
	
	/**
	 * Adds a datatype property to the model from document to literal, the datatype property is specified by a namespace and dtpName.
	 * It checks the mapping properties to see if the dtp can be directly mapped (dtp.isReified() false). 
	 * If not (dtp.isReified() true), it adds an extra level to "reify" the dtp.
	 * @param model
	 * @param document
	 * @param namespace
	 * @param dtpName
	 * @param literal
	 */
	protected void addDatatypeLiteral(Model model, String base, Resource resource, String baseURL, String namespace, String dtpName, String literal, XSDDatatype type) {
		if ((literal != null) && (literal.length() != 0)) {
			DatatypeProperty dtp = MappingConfig.getDatatypeProperty(base, namespace, dtpName);
			if (dtp != null) {
				Property dtProperty = model.getProperty(dtp.getDtpName());
				if (dtp.isReified()) {
					Resource resourceClass = model.createResource(dtp.getClassName());
					String nodeURL = baseURL + "_reification_" + namespace + "_" + dtpName + "_node" + (++nodeCounter) + "_" + Calendar.getInstance().getTimeInMillis();
					Resource node = model.createResource(nodeURL, resourceClass);
					
					Property opProperty = model.getProperty(dtp.getOpName());
					resource.addProperty(opProperty, node);
					//model.add(resource, opProperty, node);
					
					if (type == null) {
						node.addLiteral(dtProperty, literal);
					} else {
						node.addProperty(dtProperty, literal, type);
					}					
				} else {
					resource.addLiteral(dtProperty, literal);
				}				
			}
		}		
	}
	
	/**
	 * Adds an object property to the model from the RDF class "from" to the RDF class "to", the object property is specified by a namespace and an opName.
	 * @param model
	 * @param from
	 * @param to
	 * @param namespace
	 * @param opName
	 */
	protected void addObjectProperty(Model model, String base, Resource from, Resource to, String namespace, String opName) {
		String opMapping = MappingConfig.getObjectProperty(base, namespace, opName);
		if (opMapping != null) {
			Property opProperty = model.getProperty(opMapping);
			from.addProperty(opProperty, to);
		}
	}
	
	/**
	 * Adds an object property to the model from the RDF class "from" to URL string "to", the object property is specified by a namespace and an opName.
	 * @param model
	 * @param from
	 * @param to
	 * @param namespace
	 * @param opName
	 */
	protected void addObjectProperty(Model model, String base, Resource from, String to, String namespace, String opName) {		
		String opMapping = MappingConfig.getObjectProperty(base, namespace, opName);
		if (opMapping != null) {
			Resource node = model.createResource(to);
			Property opProperty = model.getProperty(opMapping);
			from.addProperty(opProperty, node);
		}
	}
	
	/**
	 * Adds an object property to the model from the URL string "from" to the RDF class "to", the object property is specified by a namespace and an opName.
	 * @param model
	 * @param from
	 * @param to
	 * @param namespace
	 * @param opName
	 */
	protected void addObjectProperty(Model model, String base, String from, Resource to, String namespace, String opName) {
		String opMapping = MappingConfig.getObjectProperty(base, namespace, opName);
		if (opMapping != null) {
			Resource node = model.createResource(from);
			Property opProperty = model.getProperty(opMapping);
			node.addProperty(opProperty, to);
		}
	}
	
	/**
	 * Adds an object property to the model from the URL string "from" to the string URL "to", the object property is specified by URL string.
	 * Use carefully as the URL string property could be anything!
	 * @param model
	 * @param from
	 * @param to
	 * @param property
	 */
	protected void addObjectProperty(Model model, Resource from, String to, String property) {
		Resource nodeFrom = model.createResource(from);
		Resource nodeTo = model.createResource(to);
		Property opProperty = model.getProperty(property);
		nodeFrom.addProperty(opProperty, nodeTo);
	}
}
