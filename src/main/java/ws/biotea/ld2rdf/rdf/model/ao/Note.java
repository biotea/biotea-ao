/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;


/**
 * @author leylajael
 *
 */
public class Note extends Annotation {
	private static final long serialVersionUID = 1L;
	/* OWL Descriptors */
	public final static String ANNOTATION_CLASS = AnnotationOntologyPrefix.AO_TYPES.getURL() + "Note";
	public final static String ANNOTATION_ID = "Note_";
	public final static String ANNOTATION_TYPE = "Note";
}
