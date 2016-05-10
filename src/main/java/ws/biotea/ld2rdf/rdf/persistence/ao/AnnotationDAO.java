package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.rdf.model.Model;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import ws.biotea.ld2rdf.exception.RDFModelIOException;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;

public interface AnnotationDAO {
	/**
	 * Inserts a new annotation.
	 * @param Annotation Annotation to be updated.
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	public URI insertAnnotation(String datasetURL, String baseURL, AnnotationE annot, String id, Model modelOut
			, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException;
	/**
	 * Inserts a new annotation.
	 * @param Annotation Annotation to be updated.
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	public URI insertAnnotation(String datasetURL, String baseURL, AnnotationE annot, String id, String fileOut
			, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException;
	/**
	 * Inserts a list of annotations.
	 * @param baseURL
	 * @param list
	 * @param fileOut
	 * @param empty
	 * @throws Exception
	 */
	public List<AnnotationE> insertAnnotations(String datasetURL, String baseURL, List<AnnotationE> list
			, Model modelOut, boolean blankNode) throws RDFModelIOException;
	/**
	 * Inserts a list of annotations.
	 * @param baseURL
	 * @param list
	 * @param fileOut
	 * @param empty
	 * @throws Exception
	 */
	public List<AnnotationE> insertAnnotations(String datasetURL, String baseURL, List<AnnotationE> list
			, String fileOut, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException;
	/**
	 * Updates an annotation.
	 * @param Annotation Annotation to be updated.
	 * @throws Exception
	 */
	public URI updateAnnotation(String datasetURL, String baseURL, AnnotationE annot, String uri
			, String fileOut, RDFFormat format, boolean empty) throws Exception;
	/**
	 * Updates an annotation.
	 * @param Annotation Annotation to be updated.
	 * @throws Exception
	 */
	public URI updateAnnotation(String datasetURL, String baseURL, AnnotationE annot, String uri
			, Model modelOut) throws Exception;
	/**
	 * Deletes an annotation given its id.
	 * @param id Annotation id to be deleted.
	 * @throws Exception
	 */
	public void deleteAnnotation(String baseURL, String id, String uri, String fileOut, RDFFormat format
			, boolean empty) throws UnsupportedOperationException;
	/**
	 * Deletes an annotation given its id.
	 * @param id Annotation id to be deleted.
	 * @throws Exception
	 */
	public void deleteAnnotation(String baseURL, String id, String uri, Model modelOut) throws UnsupportedOperationException;
}
