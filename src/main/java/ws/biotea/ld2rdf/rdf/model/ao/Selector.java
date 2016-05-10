/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.ao;

import java.io.Serializable;
import java.net.URI;

import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;


/**
 * @author leylajael
 *
 */
public abstract class Selector implements Serializable {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	private String id;
	/**
	 * URI
	 */
	private URI uri;
	private String nodeId;
	private FoafDocument document;
	private String documentId;
	/* OWL Descriptors */
	public final static String SELECTOR_CLASS = AnnotationOntologyPrefix.AO_CORE.getURL() + "Selector";
	public final static String SELECTOR_OP_ON_RESOURCE = AnnotationOntologyPrefix.AO_CORE.getURL() + "onResource";
	
	/**
	 * Constructor
	 * @param uri URI of this Selector
	 * @param document Document pointed by this Selector
	 */
	public Selector(FoafDocument document) {
		this.document = document;
	}
	/**
	 * Returns the uri.
	 * @return the uri
	 */
	public URI getUri() {
		return uri;
	}
	/**
	 * Sets the value for the id.
	 * @param id the id to set
	 */
	public void setUri(URI id) {
		this.uri = id;
	}
	/**
	 * Returns the document.
	 * @return the document
	 */
	public FoafDocument getDocument() {
		return document;
	}
	/**
	 * Returns the id.
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * Sets the value for the id.
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}	
	protected abstract String getSelector();
	protected abstract void setSelector(String str);
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}
	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((document == null) ? 0 : document.getUri().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Selector))
			return false;
		Selector other = (Selector) obj;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.getUri().equals(other.document.getUri()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
