package ws.biotea.ld2rdf.rdf.model.ao;

import java.io.Serializable;
import java.net.URI;

import ws.biotea.ld2rdf.util.OntologyPrefix;


public class FoafAgent implements Serializable {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * URI
	 */
	private URI uri;
	private String firstName, lastName;
	/* OWL Descriptors */
	public final static String FOAF_AGENT_CLASS = OntologyPrefix.FOAF.getURL() + "Agent";
	/**
	 * Returns the uri.
	 * @return the uri
	 */
	public URI getUri() {
		return uri;
	}
	/**
	 * Sets the value for the uri.
	 * @param id the uri to set
	 */
	public void setId(URI id) {
		this.uri = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " (" + this.uri + " )";
	}
}
