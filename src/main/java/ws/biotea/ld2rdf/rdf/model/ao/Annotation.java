package ws.biotea.ld2rdf.rdf.model.ao;

import ws.biotea.ld2rdf.util.Conversion;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;



/**
 * OpenAnnotation: This class represents a general annotation on a Document.
 * @author leylajael
 */
public class Annotation implements Serializable {
	private static final long serialVersionUID = 1L;

	/* Attributes */
	private String id;
	/**
	 * Label - short name with no URI (optional)
	 */
	private String label;
	/**
	 * URI
	 */
	private URI uri;
	/**
	 * If it is a blank node, it could have a node id
	 */
	private String nodeId;
	/**
	 * Body (tag) of this annotation, only one
	 */
	private Set<String> bodies = new TreeSet<String>();
	/**
	 * Topics of this annotation (semantic associations)
	 */
	private Collection<Topic> topics = new Vector<Topic>();
	/**
	 * Contexts of this annotation (portions of the document)
	 */
	private Collection<Selector> context = new Vector<Selector>();
	/**
	 * Annotated resource
	 */
	private FoafDocument document;
	private String documentID;
	/**
	 * Creation date of this annotation
	 */
	private Calendar creationDate;
	/**
	 * Comments
	 * */
	private String comment = "";
	/**
	 * Creator of this annotation
	 */
	private FoafAgent creator;
	/**
	 * Author of this annotation
	 */
	private FoafAgent author;
	
	/* OWL Descriptors */
	public final static String ANNOTATION_CLASS = AnnotationOntologyPrefix.AO_CORE.getURL() + "OpenAnnotation";
	public final static String ANNOTATION_DP_BODY = AnnotationOntologyPrefix.AO_CORE.getURL() + "body"; //TODO OP?
	public final static String ANNOTATION_OP_CONTEXT = AnnotationOntologyPrefix.AO_CORE.getURL() + "context";
	public final static String ANNOTATION_OP_HAS_TOPIC = AnnotationOntologyPrefix.AO_CORE.getURL() + "hasTopic";
	public final static String ANNOTATION_OP_ANNOTATES_RESOURCE = AnnotationOntologyPrefix.AO_CORE.getURL() + "annotatesResource";
	public final static String ANNOTATION_DP_CREATED_ON = AnnotationOntologyPrefix.PAV.getURL() + "createdOn"; //TODO: OP?
	public final static String ANNOTATION_OP_CREATED_BY = AnnotationOntologyPrefix.PAV.getURL() + "createdBy";
	public final static String ANNOTATION_OP_AUTHORED_BY = AnnotationOntologyPrefix.PAV.getURL() + "authoredBy";
	public final static String ANNOTATION_ID = "Annotation_";
	public final static String ANNOTATION_TYPE = "Annotation";

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
	 * Returns the body.
	 * @return the body
	 */
	public Set<String> getBodies() {
		return bodies;
	}
	/**
	 * Returns the topic.
	 * @return the topic
	 */
	public Collection<Topic> getTopics() {
		return topics;
	}
	/**
	 * Sets the value for the topic.
	 * @param topic the topic to set
	 */
	public void setTopics(Collection<Topic> topic) {
		this.topics = topic;
	}
	/**
	 * Adds the value for the topic.
	 * @param topic the topic to set
	 */
	public void addTopic(Topic topic) {
		this.topics.add(topic);
	}
	/**
	 * Returns the context.
	 * @return the context
	 */
	public Collection<Selector> getContext() {
		return context;
	}
	/**
	 * Sets the value for the context.
	 * @param context the context to set
	 */
	public void setContext(Collection<Selector> context) {
		this.context = context;
	}
	/**
	 * Adds the value for the context.
	 * @param context the context to set
	 */
	public void addContext(Selector context) {
		this.context.add(context);
	}
	/**
	 * Returns the resource.
	 * @return the resource
	 */
	@Deprecated
	public FoafDocument getDocument() {
		return document;
	}
	/**
	 * Returns the resource/annotated document.
	 * @return the resource
	 */
	public FoafDocument getResource() {
		return document;
	}
	/**
	 * Sets the value for the resource/annotated document.
	 * @param resource the resource to set
	 */
	public void setResource(FoafDocument resource) {
		this.document = resource;
	}
	/**
	 * Returns the creationDate (dd/mm/yyyy).
	 * @return the creationDate
	 */
	public String getCreationDateAsString() {
		return (Conversion.calendarToString(this.creationDate, '/'));
	}
	/**
	 * Sets the value for the creationDate xsd:dateTime (2010-10-24T12:48:42.361Z).
	 * @param creationDate the creationDate to set 
	 * @throws Exception 
	 */
	public void setCreationDateFromXSDDateTimeString(String creationDate) throws Exception {
		this.creationDate = Conversion.xsdDateTimeToCalendar(creationDate);
	}
	/**
	 * Returns the creator.
	 * @return the creator
	 */
	public FoafAgent getCreator() {
		return creator;
	}
	/**
	 * Returns the author.
	 * @return the author
	 */
	public FoafAgent getAuthor() {
		return author;
	}
	/**
	 * Sets the value for the creator.
	 * @param creator the creator to set
	 */
	public void setCreator(FoafAgent creator) {
		this.creator = creator;
	}
	/**
	 * Sets the value for the author.
	 * @param creator the author to set
	 */
	public void setAuthor(FoafAgent author) {
		this.author = author;
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
	/**
	 * Returns the creationDate.
	 * @return the creationDate
	 */
	public Calendar getCreationDate() {
		return creationDate;
	}
	/**
	 * Sets the value for the creationDate.
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * Returns the label.
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the value for the label.
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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
	 * @return the documentID
	 */
	public String getDocumentID() {
		return documentID;
	}
	/**
	 * @param documentID the documentID to set
	 */
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}	
	@Override
	public String toString() {
		return "[\\'" + this.getId() + "\\'" +
			"; \\'" + this.getNodeId() + "\\'" +
			"; \\'" + this.getBodies() + "\\'" +
			"; \\'" + this.getTopics() + "\\']\n";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		
		result = prime * result + ((author == null) ? 0 : author.getUri().hashCode());
		result = prime * result + ((document == null) ? 0 : document.getUri().hashCode());
		
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		
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
		if (!(obj instanceof Annotation))
			return false;
		
		Annotation other = (Annotation) obj;
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
		
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.getUri().equals(other.author.getUri()))
			return false;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.getUri().equals(other.document.getUri()))
			return false;
				
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if ( !(topics.containsAll(other.topics) && other.topics.containsAll(topics)) )
			return false;
		
		return true;
	}
	
}
