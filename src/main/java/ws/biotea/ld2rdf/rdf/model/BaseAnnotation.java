package ws.biotea.ld2rdf.rdf.model;

import ws.biotea.ld2rdf.util.OntologyPrefix;
import java.io.Serializable;

/**
 * OpenAnnotation: This class represents a general annotation on a Document.
 * @author leylajael
 */
public class BaseAnnotation implements Serializable {
	private static final long serialVersionUID = 1L;
	/* OWL Descriptors */
	public final static String ANNOTATION_DP_LABEL = OntologyPrefix.RDFS.getURL() + "label";
	public final static String RDFS_COMMENT = OntologyPrefix.RDFS.getURL() + "comment";
	public final static String BIOTEA_OCURRENCES = OntologyPrefix.BIOTEA.getURL() + "tf";
	public final static String BIOTEA_IDF = OntologyPrefix.BIOTEA.getURL() + "idf";
	public final static String RDFS_SEE_ALSO = OntologyPrefix.RDFS.getURL() + "seeAlso";
	public final static String OWL_SAME_AS = OntologyPrefix.OWL.getURL() + "sameAs";
    public final static String DC_IS_REFERENCED_BY = OntologyPrefix.DCTERMS.getURL() + "isReferencedBy";
    public final static String SIO_IN_DATASET = OntologyPrefix.SIO.getURL() + "SIO_001278"; 
    public final static String DP_SCORE = OntologyPrefix.BIOTEA.getURL() + "score"; 
}
