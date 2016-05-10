/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import java.io.Serializable;
import java.net.URI;

/**
 * @author leylajael
 *
 */
public class FoafDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * URI
	 */
	private URI uri;
	/* OWL Descriptors */
	//public final static String FOAF_CLASS = Namespace.FOAF_NS + ":Document";
	/**
	 * Returns the id.
	 * @return the id
	 */
	public URI getUri() {
		return uri;
	}
	/**
	 * Sets the value for the id.
	 * @param id the id to set
	 */
	public void setId(URI id) {
		this.uri = id;
	}
	@Override
	public String toString() {
		return "(" + this.uri + ")";
	}
}
