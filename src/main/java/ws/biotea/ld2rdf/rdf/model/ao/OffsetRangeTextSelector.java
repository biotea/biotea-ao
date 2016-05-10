/**
 * A Offset Range Text Selector identifies a string in a document through an offset 
            -  an integer indicating the distance (displacement) from the beginning of the document up until a 
            given element or point, within the same document - and a range - an integer indicating the number 
            of characters, starting from the offset, identified by the selector.
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.ClassesAndProperties;
import ws.biotea.ld2rdf.util.OntologyPrefix;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;


/**
 * @author leylajael
 *
 */
public class OffsetRangeTextSelector extends TextSelector {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * Offset: an integer indicating the distance (displacement) from the beginning of the document up until a 
            given element or point, within a document
	 */
	private long offset;
	/**
	 * Range: an integer indicating the number 
            of characters, starting from the offset, identified by the selector 
	 */
	private long range;
	/**
	 * URI where the annotation occurrs.
	 */
	private String elementURI;
	/* OWL Descriptors */
	public final static String OFFSET_RANGE_TEXT_SELECTOR_CLASS = OntologyPrefix.BIOTEA.getURL() + "OffsetRangeTextSelector";
	public final static String OFFSET_RANGE_TEXT_SELECTOR_DP_OFFSET = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "offset";
	public final static String OFFSET_RANGE_TEXT_SELECTOR_DP_RANGE = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "range";
	public final static String START_END_ELEMENT_SELECTOR_DP_LOCATOR = OntologyPrefix.DCTERMS.getURL() + ClassesAndProperties.DCTERMS_PROP_REFERENCES.getValue();
	
	/**
	 * Constructor.
	 * @param Id URI of this  OffsetRangeTextSelector
	 */
	public OffsetRangeTextSelector(FoafDocument document) {
		super(document);
	}
	/**
	 * Returns the offset.
	 * @return the offset
	 */
	public long getOffset() {
		return offset;
	}
	/**
	 * Sets the value for the offset.
	 * @param offset the offset to set
	 */
	public void setOffset(long offset) {
		this.offset = offset;
	}
	/**
	 * Returns the range.
	 * @return the range
	 */
	public long getRange() {
		return range;
	}
	/**
	 * Sets the value for the range.
	 * @param range the range to set
	 */
	public void setRange(long range) {
		this.range = range;
	}
	/**
	 * @return the elementURI
	 */
	public String getElementURI() {
		return elementURI;
	}
	/**
	 * @param elementURI the elementURI to set
	 */
	public void setElementURI(String sectionURI) {
		this.elementURI = sectionURI;
	}	
	public String toString() {
		return "(" + this.offset + " -> " + this.range + ") - " + this.elementURI;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((elementURI == null) ? 0 : elementURI.hashCode());
		result = prime * result + (int) (offset ^ (offset >>> 32));
		result = prime * result + (int) (range ^ (range >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof OffsetRangeTextSelector))
			return false;
		OffsetRangeTextSelector other = (OffsetRangeTextSelector) obj;
		if (elementURI == null) {
			if (other.elementURI != null)
				return false;
		} else if (!elementURI.equals(other.elementURI))
			return false;
		if (offset != other.offset)
			return false;
		if (range != other.range)
			return false;
		return true;
	}
	
}
