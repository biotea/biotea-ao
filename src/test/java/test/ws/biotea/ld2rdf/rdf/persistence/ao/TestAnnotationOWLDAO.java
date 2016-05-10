package test.ws.biotea.ld2rdf.rdf.persistence.ao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import ws.biotea.ld2rdf.rdf.model.ao.Annotation;
import ws.biotea.ld2rdf.rdf.model.ao.FoafAgent;
import ws.biotea.ld2rdf.rdf.model.ao.FoafDocument;
import ws.biotea.ld2rdf.rdf.model.ao.Topic;
import ws.biotea.ld2rdf.rdf.model.aoextended.AnnotationE;
import ws.biotea.ld2rdf.rdf.persistence.ao.AnnotationDAO;
import ws.biotea.ld2rdf.rdf.persistence.ao.AnnotationOWLDAO;
import ws.biotea.ld2rdf.rdf.persistence.ao.ConnectionLDModel;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.Conversion;
import ws.biotea.ld2rdf.util.annotation.AnnotationResourceConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

public class TestAnnotationOWLDAO {
	private AnnotationE annot;
	private Model model;
	private Topic topic;
	private FoafAgent creator, author;
	private FoafDocument document;

	@Before
	public void createAnnotation() throws URISyntaxException {	
		author = new FoafAgent();
		String url = AnnotationResourceConfig.getNCBOAnnotatorURL();
		author.setId(new URI(url));
		
		creator = new FoafAgent();
		creator.setId(new URI("http://biotea.ws"));

		annot = new AnnotationE();
		annot.setCreator(creator);
		annot.getBodies().add("body test");
		annot.setAuthor(author);

		Collection<Topic> topics = new ArrayList<Topic>();
		topic = new Topic();
		topic.setNameSpace(new URI("chebi:12345")); 
		topic.setURL(new URI("http://purl.obolibrary.org/obo/CHEBI_"));
		topics.add(topic);
		annot.setTopics(topics);

		document = new FoafDocument();
		Calendar now = Calendar.getInstance();
		document.setId(new URI("http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2940432"));
		annot.setResource(document);
		annot.setCreationDate(now);
	}

	@Before
	public void createModel() throws FileNotFoundException, ClassNotFoundException, OntologyLoadException {
		ConnectionLDModel conn = new ConnectionLDModel();
		model = conn.openJenaModel("test.rdf", true);		
	}

	@After
	public void closeModel() {
		model.close();
	}

	@Test
	public void insertBlankNodeAnnotation() {		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, null, model, true);
			assertNull("Blank nodes should have null URIs", uri);
			assertEquals("Annotation id should be part od node id", "Annotation_" + annot.getId(), annot.getNodeId());

			Resource resource = model.getResource(annot.getNodeId());
			assertEquals("Annotation is should be part of resource URI", "Annotation_" + annot.getId(), resource.getURI().toString());
			assertEquals("Node id should be equal to URI", annot.getNodeId(), resource.getURI().toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
	
	@Test
	public void insertAnnotation() {		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, null, model, false);
			assertNotNull("Regular nodes should not have null URIs", uri);
			assertNull("Node id for regular nodes should be null", annot.getNodeId());
			assertEquals("Annotation URI should be returned", annot.getUri(), uri);
			
			Resource resource = model.getResource(annot.getUri().toString());
			assertTrue("Annotation id should be the last part of resource URI", resource.getURI().toString().endsWith(annot.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
	
	@Test
	public void insertAnnotationGivenId() {		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, "annot_id", model, false);
			assertEquals("Annotation URI should be returned", annot.getUri(), uri);
			
			Resource resource = model.getResource(annot.getUri().toString());
			assertTrue("Annotation id should be the last part of resource URI", resource.getURI().toString().endsWith(annot.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
	
	@Test
	public void insertAnnotationWithId() {		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			annot.setId("id");
			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, null, model, false);
			assertEquals("Annotation URI should be returned", annot.getUri(), uri);
			
			Resource resource = model.getResource(annot.getUri().toString());
			assertTrue("Annotation id should be the last part of resource URI", resource.getURI().toString().endsWith(annot.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
	
	@Test
	public void insertAnnotationWithIdGivenId() {		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			annot.setId("id");
			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, "annot_id", model, false);
			assertEquals("Annotation URI should be returned", annot.getUri(), uri);
			
			Resource resource = model.getResource(annot.getUri().toString());
			assertTrue("Annotation id should be the last part of resource URI", resource.getURI().toString().endsWith(annot.getId()));
			assertEquals("Annotation id should have changed", "annot_id", annot.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
	
	@Test
	public void insertAnnotationProperties() {
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);
		Property dpBody = model.getProperty(Annotation.ANNOTATION_DP_BODY);
		Property opHasTopic = model.getProperty(Annotation.ANNOTATION_OP_HAS_TOPIC);
		Property opSameAs = model.getProperty(Annotation.OWL_SAME_AS);
		Property opAnnotatesResource = model.getProperty(Annotation.ANNOTATION_OP_ANNOTATES_RESOURCE);
		Property dpCreatedOn = model.getProperty(Annotation.ANNOTATION_DP_CREATED_ON);
		Property opCreatedBy = model.getProperty(Annotation.ANNOTATION_OP_CREATED_BY);
		Property opAuthoredBy = model.getProperty(Annotation.ANNOTATION_OP_AUTHORED_BY);
		
		
		try {			
			AnnotationDAO dao = new AnnotationOWLDAO();	

			URI uri = dao.insertAnnotation("http://base.com/", "http://biotea.ws/annotation/pmc_resource/123/", annot, null, model, false);
			Resource resource = model.getResource(uri.toString());
			
			Resource range = resource.getPropertyResourceValue(opType);
			assertEquals("Annotation class", Annotation.ANNOTATION_CLASS, range.toString());
			
			Statement statement = resource.getProperty(dpBody); 
			assertTrue("Annotation body", statement.getObject().asLiteral().getString().startsWith(annot.getBodies().iterator().next()));
			
			range = resource.getPropertyResourceValue(opHasTopic);
			assertEquals("Annotation topic", topic.getURL().toString(), range.toString());
			
			if (ResourceConfig.withBio()) {
				Resource topicRes = model.getResource(topic.getURL().toString());				
				String strIdentifier = AnnotationOntologyPrefix.toIdentifiersOrg(topic.getNameSpace().toString());
				if (strIdentifier != null) {
					range = topicRes.getPropertyResourceValue(opSameAs);
					assertEquals("Topic identifiers.org", strIdentifier, range.toString());
				}
				strIdentifier = AnnotationOntologyPrefix.toBio2RDFOrg(topic.getNameSpace().toString());
				if (strIdentifier != null) {
					range = topicRes.getPropertyResourceValue(opSameAs);
					assertEquals("Topic bio2rdf", strIdentifier, range.toString());
				}
			}
			
			range = resource.getPropertyResourceValue(opAnnotatesResource);
			assertEquals("Annotated document", document.getUri().toString(), range.toString());
			
			range = resource.getPropertyResourceValue(opCreatedBy);
			assertEquals("Annotation created by", creator.getUri().toString(), range.toString());
			
			range = resource.getPropertyResourceValue(opAuthoredBy);
			assertEquals("Annotation authored by", author.getUri().toString(), range.toString());
			
			statement = resource.getProperty(dpCreatedOn);
			statement.getObject().asLiteral().getValue().toString();
			String inModel = Conversion.calendarToString(Conversion.xsdDateTimeToCalendar(statement.getObject().asLiteral().getValue().toString()));
			assertEquals("Annotation creation date", annot.getCreationDateAsString(), inModel);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception is expected here");
		} 
	}
}
