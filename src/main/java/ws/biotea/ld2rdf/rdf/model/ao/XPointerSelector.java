/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

/**
 * @author leylajael
 *
 */
public class XPointerSelector extends Selector {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * Offset: an integer indicating the distance (displacement) from the beginning of the document up until a 
            given element or point, within a document
	 */
	private String xPointer;
	/* OWL Descriptors */
	public final static String XPOINTER_SELECTOR_CLASS = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "XPointerSelector";
	public final static String XPOINTER_SELECTOR_DP_XPOINTER = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "xpointer";

	/**
	 * Constructor.
	 * @param Id URI of this  OffsetRangeTextSelector
	 */
	public XPointerSelector(FoafDocument document) {
		super(document);
		// TODO Auto-elsevier.jaxb.generated constructor stub
	}

	/**
	 * Returns the xPointer.
	 * @return the xPointer
	 */
	public String getSelector() {
		return xPointer;
	}

	/**
	 * Sets the value for the xPointer.
	 * @param xpointer the xPointer to set
	 */
	public void setSelector(String xPointer) {
		this.xPointer = xPointer;
	}
}
