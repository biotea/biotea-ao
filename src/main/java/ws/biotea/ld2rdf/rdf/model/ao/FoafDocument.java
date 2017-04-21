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
	private URI uri;
	private String id;
	/**
	 * Returns the id.
	 * @return the id
	 */
	public URI getUri() {
		return uri;
	}
	public void setUri(URI id) {
		this.uri = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "(" + this.uri + ")";
	}
}
