/**
 * 
 */
package ws.biotea.ld2rdf.rdf.persistence.oa;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protege.exception.OntologyLoadException;

import org.apache.jena.riot.RDFFormat;
import org.apache.log4j.Logger;

import ws.biotea.ld2rdf.exception.RDFModelIOException;
import ws.biotea.ld2rdf.rdf.model.BaseAnnotation;
import ws.biotea.ld2rdf.rdf.model.ao.ElementSelector;
import ws.biotea.ld2rdf.rdf.model.ao.Selector;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;
import ws.biotea.ld2rdf.rdf.model.oa.OpenAnnotation;
import ws.biotea.ld2rdf.rdf.persistence.AnnotationDAO;
import ws.biotea.ld2rdf.rdf.persistence.ConnectionLDModel;
import ws.biotea.ld2rdf.rdfGeneration.jats.GlobalArticleConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationClassesAndProperties;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;
import ws.biotea.ld2rdf.util.mapping.MappingConfig;
import ws.biotea.ld2rdf.util.GenerateMD5;
import ws.biotea.ld2rdf.util.OntologyPrefix;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leylajael
 *
 */
public class AnnotationMappingOWLOA extends AnnotationDAO {
	Logger logger = Logger.getLogger(this.getClass());
	private List<Map<String, String>> prefixes;
	
	/**
	 * Constructor.
	 */
	public AnnotationMappingOWLOA() {
		prefixes = new ArrayList<Map<String, String>>();
		prefixes.add(OntologyPrefix.prefixesMap_RDF());
	}
	/**
	 * Adds an annotation to the model.
	 * @return
	 * @throws URISyntaxException 
	 * @throws URISyntaxException
	 * @throws OntologyLoadException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	//@Override
	public URI insertAnnotation(String base, String baseURL, AnnotationE annot, String id, Model modelOut, 
			boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException {
		createAnnotationInModel(base, baseURL, annot, id, modelOut, blankNode);
		return (annot.getUri());
	}
	
	//@Override
	public URI insertAnnotation(String base, String baseURL, AnnotationE annotation, String id, String fileOutName, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException {
		ConnectionLDModel conn = new ConnectionLDModel(this.prefixes);
		Model model = conn.openJenaModel(fileOutName, empty);		
		this.insertAnnotation(base, baseURL, annotation, id, model, blankNode);
		conn.closeAndWriteJenaModel(format);
		return (annotation.getUri());
	}
	/**
	 * Inserts a list of annotations.
	 * @param baseURL
	 * @param list
	 * @param empty
	 * @throws OntologyLoadException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws RDFModelIOException 
	 * @throws Exception
	 */
	//@Override
	public List<AnnotationE> insertAnnotations(String base, String baseURL, List<AnnotationE> list, Model modelOut,  
			boolean blankNode) throws RDFModelIOException {
		List<AnnotationE> inserted = new ArrayList<AnnotationE>();
		for (AnnotationE annotation: list) {
			try {
				createAnnotationInModel(base, baseURL, annotation, null, modelOut, blankNode);				
				inserted.add(annotation);
			} catch (Exception e) {
				//e.printStackTrace();
				logger.error("- ERROR - OpenAnnotation not inserted: There has been an error inserting annotation " + annotation + ", error: " + e.getLocalizedMessage());
			}
		}
		return inserted;
	}
	//@Override
	public List<AnnotationE> insertAnnotations(String base, String baseURL, List<AnnotationE> list
			, String fileOutName, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException {
		List<AnnotationE> inserted = new ArrayList<AnnotationE>();
		try {			
			ConnectionLDModel conn = new ConnectionLDModel(this.prefixes);
			Model model = conn.openJenaModel(fileOutName, empty);	
			inserted = this.insertAnnotations(base, baseURL, list, model, blankNode);
			conn.closeAndWriteJenaModel(format);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.fatal("- FATAL - Annotations model " + fileOutName + " was not closed/saved: " + e.getMessage());
			throw new RDFModelIOException(e);
		}
		//File file = new File(fileOutName);
		logger.info("==Annotated RDF ==" + fileOutName + " ANNOT: " + inserted.size());
		if (inserted.size() == 0) {
			File file = new File(fileOutName);
			file.delete();
		}
		return inserted;
	}
	/**
	 * 
	 * @param model
	 * @param baseURL
	 * @param annotation
	 * @param id
	 * @throws URISyntaxException
	 */
	private void createAnnotationInModel (String base, String baseURL, AnnotationE annotation, String id, Model model, 
			boolean blankNode)  throws URISyntaxException {
		
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);		
		Property dpLabel = model.getProperty(BaseAnnotation.ANNOTATION_DP_LABEL);
		Property rdfsComment = model.getProperty(BaseAnnotation.RDFS_COMMENT);
		Property opSeeAlso = model.getProperty(BaseAnnotation.RDFS_SEE_ALSO);
		Property opSameAs = model.getProperty(BaseAnnotation.OWL_SAME_AS);
		
		baseURL += annotation.getDocumentID();
		
		//OpenAnnotation, Body (literal), and createdOn (date)
		if (id == null) {
			StringBuffer signature = new StringBuffer();
			signature.append(annotation.getAuthor().toString());
			signature.append(annotation.getResource().toString());
			signature.append(annotation.getTopics());
			annotation.setId(GenerateMD5.getInstance().getMD5Hash(signature.toString()));
		} else {
			annotation.setId(id);
		}
		String idPrefix = OpenAnnotation.ANNOTATION_ID;
		try {
			idPrefix = annotation.getClass().getField("ANNOTATION_ID").get(null).toString();
		} catch (Exception e) {} 
		String annotationClazz = MappingConfig.getClass(base, "oa", "Annotation");
				
		Resource annotationRes;
		Resource annotationClass = model.createResource(annotationClazz);
		if (blankNode) {
			if (annotation.getDocumentID() != null) {
				annotation.setNodeId(annotation.getDocumentID() + "_" + idPrefix + annotation.getId());
			} else {
				annotation.setNodeId(idPrefix + annotation.getId());
			}
			annotation.setUri(null);
			annotationRes = model.createResource(new AnonId(annotation.getNodeId()));
			annotationRes.addProperty(opType, annotationClass);
		} else {
			annotation.setNodeId(null);
			String resourceURI = baseURL + "_" + annotation.getId();
			annotation.setUri(new URI(resourceURI));
			annotationRes = model.createResource(annotation.getUri().toString(), annotationClass);
			if (!annotationClazz.equals(OpenAnnotation.ANNOTATION_CLASS)) {
				annotationRes.addProperty(opType, annotationClass);
			}
		}
		
		//provenance
		this.addDatatypeLiteral(model, base, annotationRes, baseURL, "pav", "createdOn", 
			DateFormat.getDateInstance(DateFormat.DEFAULT).format(annotation.getCreationDate().getTime()), null);
		Resource resCreator = model.createResource(annotation.getCreator().getUri().toString());
		this.addObjectProperty(model, base, annotationRes, resCreator, "pav", "createdBy");
		Resource resAuthor = model.createResource(annotation.getAuthor().getUri().toString());
		this.addObjectProperty(model, base, annotationRes, resAuthor, "pav", "authoredBy");
		
		//body
		int textualBody = 1;
		Resource annotationTextualBodyClass = model.createResource(MappingConfig.getClass(base, "oa", "TextualBody"));
		for (String body: annotation.getBodies()) {
			Resource annotationTextualBody = model.createResource(new AnonId("TextualBody_" + textualBody + "_" + annotation.getId() ));			
			annotationTextualBody.addProperty(opType, annotationTextualBodyClass);
			this.addDatatypeLiteral(model, base, annotationTextualBody, baseURL, "rdf", "value", body, null);
			
			this.addObjectProperty(model, base, annotationRes, annotationTextualBody, "oa", "hasBody");
			textualBody++;
		}	
		//body topics
		for (ws.biotea.ld2rdf.rdf.model.ao.Topic topic:annotation.getTopics()) {
			Resource resTopic = model.createResource(topic.getURL().toString());
			this.addObjectProperty(model, base, annotationRes, resTopic, "oa", "hasBody");
			if (ResourceConfig.withBio()) {
				String strIdentifier = AnnotationOntologyPrefix.toIdentifiersOrg(topic.getNameSpace().toString());
				if (strIdentifier != null) {
					Resource resIdentifier = model.createResource(strIdentifier);
					resTopic.addProperty(opSameAs, resIdentifier);
				}
	            strIdentifier = AnnotationOntologyPrefix.toBio2RDFOrg(topic.getNameSpace().toString());
	            if (strIdentifier != null) {
	                Resource resIdentifier = model.createResource(strIdentifier);
	                resTopic.addProperty(opSameAs, resIdentifier);
	            }	            
			}  
			String cui = AnnotationOntologyPrefix.getCUI(topic.getURL().toString());
            if (cui != null) {
            	this.addDatatypeLiteral(model, base, resTopic, baseURL, "umls", "cui", cui, null);
            	for (String sty: topic.getUmlsType()) {
        			this.addDatatypeLiteral(model, base, resTopic, baseURL, "umls", "tui", sty, null);
            		
            		Resource typeClass = model.createResource(AnnotationClassesAndProperties.UMLS_STY.getURLValue() + "/" + sty);
            		this.addObjectProperty(model, base ,resTopic, typeClass, "umls", "hasSTY");
            	}
            } 
			for (URI seeAlso: topic.getSeeAlso()) {
				Resource resIdentifier = model.createResource(seeAlso.toString());
				resTopic.addProperty(opSeeAlso, resIdentifier);
			}
			if ((topic.getComment() != null) && (topic.getComment().length() != 0)) {
				resTopic.addLiteral(rdfsComment, topic.getComment());
			}
		}		
		
		//Context		
		if (ResourceConfig.getKeepSelector()) {
			for (Selector selector:annotation.getContext()) {			
				if (selector instanceof ElementSelector) {
					ElementSelector element = (ElementSelector)selector;
					if (element.getElementURI().equals(annotation.getResource().getUri().toString())) {
						Resource resDocument = model.createResource(GlobalArticleConfig.getArticleRdfUri(ResourceConfig.getBioteaBase(base), annotation.getDocumentID())); 
						this.addObjectProperty(model, base, annotationRes, resDocument, "oa", "hasTarget");
					} else {
						String resSelectorURI = element.getElementURI();
						if (!resSelectorURI.startsWith("http://")) {
							resSelectorURI = GlobalArticleConfig.getArticleParagraphRdfUri(ResourceConfig.getBioteaBase(base), annotation.getDocumentID(), resSelectorURI);
						}	
						Resource resSelector = model.createResource(resSelectorURI);
						Resource resDocument = model.createResource(GlobalArticleConfig.getArticleRdfUri(ResourceConfig.getBioteaBase(base), annotation.getDocumentID()));
						this.addObjectProperty(model, base, resSelector, resDocument, "oa", "hasSource");
						this.addObjectProperty(model, base, annotationRes, resSelector, "oa", "hasTarget");
					}					
				}
			}
			if (annotation.getContext().isEmpty()) {
				//annotated resource
				Resource resDocument = model.createResource(GlobalArticleConfig.getArticleRdfUri(ResourceConfig.getBioteaBase(base), annotation.getDocumentID()));
				this.addObjectProperty(model, base, annotationRes, resDocument, "oa", "hasTarget");
			}
		} else {
			//annotated resource
			Resource resDocument = model.createResource(GlobalArticleConfig.getArticleRdfUri(ResourceConfig.getBioteaBase(base), annotation.getDocumentID()));
			this.addObjectProperty(model, base, annotationRes, resDocument, "oa", "hasTarget");
		}	
		
		//others
		if (annotation.getLabel() != null) {
			annotationRes.addLiteral(dpLabel, annotation.getLabel());
		}
		if ((annotation.getComment() != null) && (annotation.getComment().length() != 0)) {
			annotationRes.addLiteral(rdfsComment, annotation.getComment());
		}
		if (annotation.getFrequency() != null) {
			this.addDatatypeLiteral(model, base, annotationRes, baseURL, "biotea", "tf", "" + annotation.getFrequency(), XSDDatatype.XSDint);
		}		
		if (annotation.getIDF() != null) {
			this.addDatatypeLiteral(model, base, annotationRes, baseURL, "biotea", "idf", "" + annotation.getIDF(), XSDDatatype.XSDdouble);
		}
		if (annotation.getScore() != null) {
			this.addDatatypeLiteral(model, base, annotationRes, baseURL, "biotea", "score", "" + annotation.getScore(), XSDDatatype.XSDdouble);
		}
	}
	//@Override
	public void deleteAnnotation(String baseURL, String id, String uri, String fileOut, RDFFormat format, boolean empty) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	//@Override
	public void deleteAnnotation(String baseURL, String id, String uri, Model modelOut) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	//@Override
	public URI updateAnnotation(String base, String baseURL, AnnotationE annotation, String uri
			, String fileOut, RDFFormat format, boolean empty) throws Exception {
		this.deleteAnnotation(baseURL, annotation.getId(), uri, fileOut, format, empty);
		return (this.insertAnnotation(base, baseURL, annotation, annotation.getId(), fileOut
				, format, empty, annotation.getUri() == null));
	}
	//@Override
	public URI updateAnnotation(String base, String baseURL, AnnotationE annotation, String uri, Model modelOut) throws Exception {
		this.deleteAnnotation(baseURL, annotation.getId(), uri, modelOut);
		return (this.insertAnnotation(base, baseURL, annotation, annotation.getId(), modelOut, annotation.getUri() == null));
	}	
}
