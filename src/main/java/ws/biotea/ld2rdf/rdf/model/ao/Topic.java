package ws.biotea.ld2rdf.rdf.model.ao;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;
	/* Attributes */
	/**
	 * URI
	 */
	private URI nameSpace;
	private URI url;
	private List<URI> seeAlso = new ArrayList<URI>();
	private List<String> umlsType = new ArrayList<String>();
	private String comment;	
	/**
	 * Returns the nameSpace.
	 * @return the nameSpace
	 */
	public URI getNameSpace() {
		return nameSpace;
	}
	/**
	 * Sets the value for the nameSpace.
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(URI namespace) {
		this.nameSpace = namespace;
	}	
	
	/**
	 * @return the url
	 */
	public URI getURL() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setURL(URI url) {
		this.url = url;
	}
	/**
	 * @return the seeAlso
	 */
	public List<URI> getSeeAlso() {
		return seeAlso;
	}
	/**
	 * @param seeAlso the seeAlso to set
	 */
	public void setSeeAlso(List<URI> seeAlso) {
		this.seeAlso = seeAlso;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the type
	 */
	public List<String> getUmlsType() {
		return umlsType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nameSpace == null) ? 0 : nameSpace.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		if (!(obj instanceof Topic))
			return false;
		Topic other = (Topic) obj;
		if (nameSpace == null) {
			if (other.nameSpace != null)
				return false;
		} else if (!nameSpace.equals(other.nameSpace))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public String toString() {
		return (this.url.toString());
	}
}
