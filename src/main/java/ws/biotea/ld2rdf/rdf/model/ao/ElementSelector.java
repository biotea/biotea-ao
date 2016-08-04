package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.ClassesAndProperties;
import ws.biotea.ld2rdf.util.OntologyPrefix;

public class ElementSelector extends Selector{
	private static final long serialVersionUID = 1L;
	private String elementURI;
	private String text;
	/* OWL Descriptors */
	public final static String ELEMENT_SELECTOR_CLASS = OntologyPrefix.BIOTEA.getURL() + "ElementSelector";
	public final static String ELEMENT_SELECTOR_DP_LOCATOR = OntologyPrefix.DCTERMS.getURL() + ClassesAndProperties.DCTERMS_PROP_REFERENCES.getValue();
	
	public ElementSelector(FoafDocument document) {
		super(document);
	}
	@Override
	public String getSelector() {
		return text == null ? "" : text;
	}
	@Override
	public void setSelector(String str) {
		this.text = str;
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
	public void setElementURI(String elementURI) {
		this.elementURI = elementURI;
	}
	public boolean equals(Object o) {
		if (o instanceof ElementSelector) {
			ElementSelector ses = (ElementSelector)o;
			return (this.elementURI.equals(ses.elementURI));
		}
		return false;
	}
	public String toString() {
		return this.elementURI;
	}
}
