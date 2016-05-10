/**
 * 
 */
package ws.biotea.ld2rdf.rdf.persistence.ao;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protege.exception.OntologyLoadException;

import org.apache.jena.riot.RDFFormat;
import org.apache.log4j.Logger;

import ws.biotea.ld2rdf.exception.RDFModelIOException;
import ws.biotea.ld2rdf.rdf.model.ao.Annotation;
import ws.biotea.ld2rdf.rdf.model.ao.ElementSelector;
import ws.biotea.ld2rdf.rdf.model.ao.OffsetRangeTextSelector;
import ws.biotea.ld2rdf.rdf.model.ao.Selector;
import ws.biotea.ld2rdf.rdf.model.ao.StartEndElementSelector;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;
import ws.biotea.ld2rdf.util.annotation.AnnotationClassesAndProperties;
import ws.biotea.ld2rdf.util.annotation.AnnotationResourceConfig;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;
import ws.biotea.ld2rdf.util.GenerateMD5;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leylajael
 *
 */
public class AnnotationOWLDAO implements AnnotationDAO {
	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * Constructor.
	 */
	public AnnotationOWLDAO() {}
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
	public URI insertAnnotation(String datasetURL, String baseURL,
			AnnotationE annot, String id, Model modelOut, 
			boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException {
		createAnnotationInModel(modelOut, datasetURL, baseURL, annot, id, blankNode);
		return (annot.getUri());
	}
	
	//@Override
	public URI insertAnnotation(String datasetURL, String baseURL, AnnotationE annotation, String id, String fileOutName, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException, FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException {
		ConnectionLDModel conn = new ConnectionLDModel();
		Model model = conn.openJenaModel(fileOutName, empty);		
		this.insertAnnotation(datasetURL, baseURL, annotation, id, model, blankNode);
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
	public List<AnnotationE> insertAnnotations(String datasetURL,
			String baseURL, List<AnnotationE> list, Model modelOut,  
			boolean blankNode) throws RDFModelIOException {
		List<AnnotationE> inserted = new ArrayList<AnnotationE>();
		for (AnnotationE annotation: list) {
			try {
				createAnnotationInModel(modelOut, datasetURL, baseURL, annotation, null, blankNode);				
				inserted.add(annotation);
			} catch (Exception e) {
				//e.printStackTrace();
				logger.error("- ERROR - Annotation not inserted: There has been an error inserting annotation " + annotation + ", error: " + e.getLocalizedMessage());
			}
		}
		return inserted;
	}
	//@Override
	public List<AnnotationE> insertAnnotations(String datasetURL, String baseURL, List<AnnotationE> list
			, String fileOutName, RDFFormat format, boolean empty, boolean blankNode) throws RDFModelIOException {
		List<AnnotationE> inserted = new ArrayList<AnnotationE>();
		try {			
			ConnectionLDModel conn = new ConnectionLDModel();
			Model model = conn.openJenaModel(fileOutName, empty);	
			inserted = this.insertAnnotations(datasetURL, baseURL, list, model, blankNode);
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
	private void createAnnotationInModel(Model model, String datasetURL, String baseURL, AnnotationE annotation
			, String id, boolean blankNode) throws URISyntaxException {
		//OntClass annotationClass = model.getOntClass(Annotation.ANNOTATION_CLASS);
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);
		Property opHasSTY = model.getProperty(AnnotationClassesAndProperties.UMLS_HAS_STY.getURLValue());
		Property dpBody = model.getProperty(Annotation.ANNOTATION_DP_BODY);
		Property opContext = model.getProperty(Annotation.ANNOTATION_OP_CONTEXT);
		Property opHasTopic = model.getProperty(Annotation.ANNOTATION_OP_HAS_TOPIC);
		Property opAnnotatesResource = model.getProperty(Annotation.ANNOTATION_OP_ANNOTATES_RESOURCE);
		Property dpCreatedOn = model.getProperty(Annotation.ANNOTATION_DP_CREATED_ON);
		Property opCreatedBy = model.getProperty(Annotation.ANNOTATION_OP_CREATED_BY);
		Property opAuthoredBy = model.getProperty(Annotation.ANNOTATION_OP_AUTHORED_BY);
		Property dpLabel = model.getProperty(Annotation.ANNOTATION_DP_LABEL);
		Property rdfsComment = model.getProperty(Annotation.RDFS_COMMENT);
		Property bioteaOcurrences = model.getProperty(Annotation.BIOTEA_OCURRENCES);
		Property bioteaIDF = model.getProperty(Annotation.BIOTEA_IDF);
		Property opSeeAlso = model.getProperty(Annotation.RDFS_SEE_ALSO);
		Property opSameAs = model.getProperty(Annotation.OWL_SAME_AS);
		Property dpCUI = model.getProperty(AnnotationClassesAndProperties.UMLS_CUI.getURLValue());
		Property dpTUI = model.getProperty(AnnotationClassesAndProperties.UMLS_TUI.getURLValue());
		Property dpScore = model.getProperty(Annotation.DP_SCORE);
		
		//Annotation, Body (literal), and createdOn (date)
		if (id == null) {
			StringBuffer signature = new StringBuffer();
			signature.append(annotation.getAuthor().toString());
			signature.append(annotation.getResource().toString());
			signature.append(annotation.getTopics());
			annotation.setId(GenerateMD5.getInstance().getMD5Hash(signature.toString()));
		} else {
			annotation.setId(id);
		}
		String idPrefix = Annotation.ANNOTATION_ID;
		try {
			idPrefix = annotation.getClass().getField("ANNOTATION_ID").get(null).toString();
		} catch (Exception e) {} 
		String annotationClazz = Annotation.ANNOTATION_CLASS;
		try {
			annotationClazz = annotation.getClass().getField("ANNOTATION_CLASS").get(null).toString();
		} catch (Exception e) {}
				
		Resource annotationRes;
		Resource annotationClass = model.createResource(annotationClazz);
		//Resource basicAnnotationClass = model.createResource(Annotation.ANNOTATION_CLASS); 
		//Resource annotationRes = model.createIndividual(resourceURI, annotationClass).addLiteral(dpBody, annotation.getBody());
		if (blankNode) {
			if (annotation.getDocumentID() != null) {
				annotation.setNodeId(annotation.getDocumentID() + "_" + idPrefix + annotation.getId());
			} else {
				annotation.setNodeId(idPrefix + annotation.getId());
			}
			annotation.setUri(null);
			annotationRes = model.createResource(new AnonId(annotation.getNodeId()));
			annotationRes.addProperty(opType, annotationClass);
			/*if (!annotationClazz.equals(Annotation.ANNOTATION_CLASS)) {
				annotationRes.addProperty(opType, basicAnnotationClass);
			}*/
		} else {
			annotation.setNodeId(null);
			String resourceURI = baseURL + annotation.getId();
			annotation.setUri(new URI(resourceURI));
			annotationRes = model.createResource(annotation.getUri().toString(), annotationClass);
			if (!annotationClazz.equals(Annotation.ANNOTATION_CLASS)) {
				annotationRes.addProperty(opType, annotationClass);
			}
		}
		//annotationRes.addProperty(opInDataset, annotationClass);
		for (String body: annotation.getBodies()) {
			annotationRes.addLiteral(dpBody, body);
		}		
		annotationRes.addLiteral(dpCreatedOn, annotation.getCreationDate());
		if (annotation.getLabel() != null) {
			annotationRes.addLiteral(dpLabel, annotation.getLabel());
		}
		//comments and number_items
		if ((annotation.getComment() != null) && (annotation.getComment().length() != 0)) {
			annotationRes.addLiteral(rdfsComment, annotation.getComment());
		}
		if (annotation.getFrequency() != null) {
			annotationRes.addProperty(bioteaOcurrences, "" + annotation.getFrequency(), XSDDatatype.XSDint);
		}		
		if (annotation.getIDF() != null) {
			annotationRes.addProperty(bioteaIDF, "" + annotation.getIDF(), XSDDatatype.XSDdouble);
		}
		if (annotation.getScore() != null) {
			annotationRes.addProperty(dpScore, "" + annotation.getScore(), XSDDatatype.XSDdouble);
		}
		//Context		
		if (ResourceConfig.getKeepSelector()) {
			SelectorDAO<StartEndElementSelector> startEndElementOWL = new StartEndElementSelectorOWL();
			SelectorDAO<ElementSelector> elementOWL = new ElementSelectorOWL();
			SelectorDAO<OffsetRangeTextSelector> offsetSelectorOWL = new OffsetRangeTextSelectorOWL();
			for (Selector selector:annotation.getContext()) {			
				if (selector instanceof StartEndElementSelector) {
					if (AnnotationResourceConfig.keepStartEnd()) {
						StartEndElementSelector ses = (StartEndElementSelector)selector;
						Resource resContext = startEndElementOWL.addSelector(ses, model);
						model.add(annotationRes, opContext, resContext);
					} else {
						StartEndElementSelector temp = (StartEndElementSelector)selector;
						ElementSelector ses = new ElementSelector(temp.getDocument());
						ses.setDocumentId(temp.getDocumentId());
						ses.setElementURI(temp.getElementURI());
						ses.setSelector(temp.getSelector());
						ses.setUri(temp.getUri());
						Resource resContext = elementOWL.addSelector(ses, model);
						model.add(annotationRes, opContext, resContext);
					}				
				} else if (selector instanceof ElementSelector) {
					ElementSelector ses = (ElementSelector)selector;
					Resource resContext = elementOWL.addSelector(ses, model);
					model.add(annotationRes, opContext, resContext);
				} else if (selector instanceof OffsetRangeTextSelector) {
					OffsetRangeTextSelector ses = (OffsetRangeTextSelector)selector;
					Resource resContext = offsetSelectorOWL.addSelector(ses, model);				
					model.add(annotationRes, opContext, resContext);
				}
			}		
		}		
		//Topics
		for (ws.biotea.ld2rdf.rdf.model.ao.Topic topic:annotation.getTopics()) {
			Resource resTopic = model.createResource(topic.getURL().toString());
			annotationRes.addProperty(opHasTopic, resTopic);
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
            	resTopic.addLiteral(dpCUI, cui);
            	for (String sty: topic.getUmlsType()) {
            		resTopic.addLiteral(dpTUI, sty);
            		Resource typeClass = model.createResource(AnnotationClassesAndProperties.UMLS_STY.getURLValue() + "/" + sty);
            		resTopic.addProperty(opHasSTY, typeClass);
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
		//annotated resource
		Resource resDocument = model.createResource(annotation.getResource().getUri().toString()); 
		annotationRes.addProperty(opAnnotatesResource, resDocument);
		//created by
		Resource resCreator = model.createResource(annotation.getCreator().getUri().toString());
		annotationRes.addProperty(opCreatedBy, resCreator);
		//authored by
		Resource resAuthor = model.createResource(annotation.getAuthor().getUri().toString());
		annotationRes.addProperty(opAuthoredBy, resAuthor);
	}
	//@Override
	public void deleteAnnotation(String baseURL, String id, String uri, String fileOut, RDFFormat format, boolean empty) throws UnsupportedOperationException {
		/*ConnectionLDModel conn = new ConnectionLDModel();
		JenaOWLModel owlModel = conn.openOWLModel(uri, fileOut, empty);
		
		String resourceURI = baseURL + "Annotation_" + id;
		//Delete
		OWLIndividual ind = owlModel.getOWLIndividual(resourceURI);
		ind.delete();
		//Delete selectors
		OffsetRangeTextSelectorOWL ortsOWL = new OffsetRangeTextSelectorOWL();	
		ortsOWL.deleteAnnotationSelector(id, owlModel);
		XPointerSelectorOWL xpointerOWL = new XPointerSelectorOWL();
		xpointerOWL.deleteAnnotationSelector(id, owlModel);
		
		conn.closeAndWriteOWLModel();*/
		throw new UnsupportedOperationException();
	}
	//@Override
	public void deleteAnnotation(String baseURL, String id, String uri, Model modelOut) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	//@Override
	public URI updateAnnotation(String datasetURL, String baseURL, AnnotationE annotation, String uri
			, String fileOut, RDFFormat format, boolean empty) throws Exception {
		this.deleteAnnotation(baseURL, annotation.getId(), uri, fileOut, format, empty);
		return (this.insertAnnotation(datasetURL, baseURL, annotation, annotation.getId(), fileOut
				, format, empty, annotation.getUri() == null));
	}
	//@Override
	public URI updateAnnotation(String datasetURL, String baseURL, AnnotationE annotation, String uri, Model modelOut) throws Exception {
		this.deleteAnnotation(baseURL, annotation.getId(), uri, modelOut);
		return (this.insertAnnotation(datasetURL, baseURL, annotation, annotation.getId(), modelOut, annotation.getUri() == null));
	}	
}
