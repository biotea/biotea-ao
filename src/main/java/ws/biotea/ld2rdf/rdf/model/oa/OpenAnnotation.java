package ws.biotea.ld2rdf.rdf.model.oa;

import ws.biotea.ld2rdf.util.OntologyPrefix;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;
import java.io.Serializable;

/**
 * OpenAnnotation: This class represents a general open annotation on a Document.
 * @author leylajael
 */
public class OpenAnnotation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* OWL Descriptors */
	public final static String ANNOTATION_CLASS = AnnotationOntologyPrefix.OA_CORE.getURL() + "Annotation";
	
	public final static String ANNOTATION_OP_BODY = AnnotationOntologyPrefix.OA_CORE.getURL() + "hasBody";
	public final static String TEXTUAL_BODY_CLASS = AnnotationOntologyPrefix.OA_CORE.getURL() + "TextualBody";
	public final static String ANNOTATION_OP_VALUE = OntologyPrefix.RDF.getURL() + "value";
	
	public final static String ANNOTATION_DP_CREATED_ON = AnnotationOntologyPrefix.PAV.getURL() + "createdOn";
	public final static String ANNOTATION_OP_CREATED_BY = AnnotationOntologyPrefix.PAV.getURL() + "createdBy";
	public final static String ANNOTATION_OP_AUTHORED_BY = AnnotationOntologyPrefix.PAV.getURL() + "authoredBy";
	
	public final static String ANNOTATION_OP_TARGET = AnnotationOntologyPrefix.OA_CORE.getURL() + "hasTarget";
	public final static String SPECIFIC_RESOURCE_CLASS = AnnotationOntologyPrefix.OA_CORE.getURL() + "SpecificResource";
	public final static String ANNOTATION_OP_SOURCE = AnnotationOntologyPrefix.OA_CORE.getURL() + "hasSource";
	
	public final static String ANNOTATION_ID = "OpenAnnotation_";
	public final static String ANNOTATION_TYPE = "OpenAnnotation";
}
