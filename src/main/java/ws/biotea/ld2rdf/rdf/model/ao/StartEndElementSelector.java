/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.ClassesAndProperties;
import ws.biotea.ld2rdf.util.OntologyPrefix;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

/**
 * @author Leyla Garcia
 *
 */
public class StartEndElementSelector extends TextSelector{
	private static final long serialVersionUID = 1L;
	private int start, end;
	private String elementURI;
	/* OWL Descriptors */
	public final static String START_END_ELEMENT_SELECTOR_CLASS = OntologyPrefix.BIOTEA.getURL() + "StartEndElementSelector";
	public final static String START_END_ELEMENT_SELECTOR_DP_INIT = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "init";
	public final static String START_END_ELEMENT_SELECTOR_DP_END = AnnotationOntologyPrefix.AO_SELECTORS.getURL() + "end";
	public final static String START_END_ELEMENT_SELECTOR_DP_LOCATOR = OntologyPrefix.DCTERMS.getURL() + ClassesAndProperties.DCTERMS_PROP_REFERENCES.getValue();
	
	public StartEndElementSelector(FoafDocument document) {
		super(document);
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
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
	public boolean equals(Object o) {
		if (o instanceof StartEndElementSelector) {
			StartEndElementSelector ses = (StartEndElementSelector)o;
			return (this.start == ses.start) && (this.end == ses.end) && (this.elementURI.equals(ses.elementURI));
		}
		return false;
	}
	public String toString() {
		return "(" + this.start + "," + this.end + ") - " + this.elementURI;
	}
}
