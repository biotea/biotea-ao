package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.net.URI;
import java.net.URISyntaxException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.ibm.icu.util.Calendar;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import ws.biotea.ld2rdf.rdf.model.ao.ElementSelector;
import ws.biotea.ld2rdf.rdf.model.ao.Selector;
import ws.biotea.ld2rdf.rdfGeneration.jats.GlobalArticleConfig;
import ws.biotea.ld2rdf.util.GenerateMD5;
import ws.biotea.ld2rdf.util.ResourceConfig;

public class ElementSelectorOWL implements SelectorDAO<ElementSelector> {

	public Resource addSelector(ElementSelector selector, OntModel model, String base)
			throws URISyntaxException {
		return null;
	}

	public Resource addSelector(ElementSelector selector, Model model, String base, String baseURL, String documentID) throws URISyntaxException {
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);
		Property opOnResource = model.getProperty(Selector.SELECTOR_OP_ON_RESOURCE);
		Property opLocator = model.getProperty(ElementSelector.ELEMENT_SELECTOR_DP_LOCATOR);
		if (selector.getId() == null) {
			selector.setId(
				GenerateMD5.getInstance().getMD5Hash(selector.getElementURI()) + 
					"_" + selector.getSelector().replaceAll("[^A-Za-z0-9]", "") + "_" + Calendar.getInstance().getTimeInMillis());
		}
		
		String resourceURI = baseURL + "_Selector_" + selector.getId();
		selector.setUri(new URI(resourceURI));
		
		Resource selectorClass = model.createResource(ElementSelector.ELEMENT_SELECTOR_CLASS);
		Resource selectorRes = model.createResource(selector.getUri().toString(), selectorClass);		
		selectorRes.addProperty(opType, selectorClass);
		
		Resource resDocument = model.createResource(GlobalArticleConfig.getArticleRdfUri(ResourceConfig.getBioteaBase(base), documentID)); 
		selectorRes.addProperty(opOnResource, resDocument);
		
		String elemSelectorURI = selector.getElementURI();
		if (!elemSelectorURI.startsWith("http://")) {
			elemSelectorURI = GlobalArticleConfig.getArticleParagraphRdfUri(ResourceConfig.getBioteaBase(base), documentID, elemSelectorURI);
		}
		Resource resLocator = model.createResource(elemSelectorURI);
		model.add(selectorRes, opLocator, resLocator);
		
		return selectorRes;
	}

	public void deleteSelector(long id, JenaOWLModel owlModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteAnnotationSelector(long id, JenaOWLModel owlModel)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
