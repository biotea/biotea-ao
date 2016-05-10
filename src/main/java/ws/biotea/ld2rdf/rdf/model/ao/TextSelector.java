/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;


/**
 * @author leylajael
 *
 */
public abstract class TextSelector extends Selector {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * Annotated text
	 */
	private String text;
	/* OWL Descriptors */
	public final static String TEXT_SELECTOR_CLASS = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "TextSelector";
	public final static String TEXT_SELECTOR_DP_EXACT = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "exact";
	/**
	 * Constructor.
	 * @param Id URI of this TextSelector
	 */
	public TextSelector(FoafDocument document) {
		super(document);
	}
	/**
	 * Returns the text.
	 * @return the text
	 */
	public String getSelector() {
		return text;
	}
	/**
	 * Sets the value for the text.
	 * @param text the text to set
	 */
	public void setSelector(String text) {
		this.text = text;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		if (!(obj instanceof TextSelector))
			return false;
		TextSelector other = (TextSelector) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}