package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.riot.RDFFormat;
import org.apache.log4j.Logger;

import ws.biotea.ld2rdf.rdf.model.ao.Annotation;
import ws.biotea.ld2rdf.rdf.model.ao.FoafDocument;
import ws.biotea.ld2rdf.rdf.model.ao.Topic;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationClassesAndProperties;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.stanford.smi.protege.exception.OntologyLoadException;

public class AnnotationOWLReader {
	private final static Logger LOGGER = Logger.getLogger(AnnotationOWLReader.class);
	/**
	 * Retrieves annotations from an RDF/XML file.
	 * @param doc
	 * @return
	 * @throws OntologyLoadException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws URISyntaxException 
	 */
	public static List<AnnotationE> retrieveFromFile(File doc, RDFFormat format) throws FileNotFoundException, ClassNotFoundException, OntologyLoadException, URISyntaxException {
		List<AnnotationE> lst = new ArrayList<AnnotationE>();
		ConnectionLDModel conn = new ConnectionLDModel();
		Model model = conn.openJenaModel(doc.getAbsolutePath(), false, format);
		
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);
		Property bioteaOcurrences = model.getProperty(Annotation.BIOTEA_OCURRENCES);
		Property bioteaIDF = model.getProperty(Annotation.BIOTEA_IDF);
		Property opHasTopic = model.getProperty(Annotation.ANNOTATION_OP_HAS_TOPIC);
		Property dpTUI = model.getProperty(AnnotationClassesAndProperties.UMLS_TUI.getURLValue());
		Property opAnnotatesResource = model.getProperty(Annotation.ANNOTATION_OP_ANNOTATES_RESOURCE);
		
		String annotationClazz = Annotation.ANNOTATION_CLASS;
		Resource annotationClass = model.createResource(annotationClazz);
		ResIterator itr = model.listResourcesWithProperty(opType, annotationClass);
		while (itr.hasNext()) {
			Resource r = itr.nextResource();
			
			AnnotationE annot = new AnnotationE();
		    
		    try {
		    	annot.setFrequency(r.getProperty(bioteaOcurrences).getInt());
		    } catch (Exception e) {
		    	annot.setFrequency(0);
		    }
		    try {
		    	annot.setIDF(r.getProperty(bioteaIDF).getDouble());
		    } catch (Exception e) {
		    	annot.setIDF(0.0);
		    }
		    
		    Resource annotRes = r.getPropertyResourceValue(opAnnotatesResource);
		    FoafDocument annotDoc = new FoafDocument();
		    annotDoc.setId(new URI(annotRes.getURI()));
		    annot.setResource(annotDoc);
		    StmtIterator stmItr = r.listProperties(opHasTopic);
		    while (stmItr.hasNext()) {
		    	Statement stm = stmItr.next();	
		    	String topicURI = stm.getObject().asResource().getURI();
		    	try {
		    		Topic topic = new Topic();
					topic.setURL(new URI(topicURI));
					annot.addTopic(topic);
					
					StmtIterator typeItr = stm.getObject().asResource().listProperties(dpTUI);
					while (typeItr.hasNext()) {
						String sty = typeItr.next().getObject().asLiteral().getString();
						topic.getUmlsType().add(sty);
					}
					
				} catch (URISyntaxException e) {
					LOGGER.warn(topicURI + " URI could not been created, such topic has been excluded from output");
				}		    	
		    }
		    
		    lst.add(annot);
		}
		model.close();
		return lst;
	}
}
