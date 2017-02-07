/**
 * 
 */
package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.net.URI;
import java.net.URISyntaxException;



import ws.biotea.ld2rdf.rdf.model.ao.OffsetRangeTextSelector;
import ws.biotea.ld2rdf.rdf.model.ao.Selector;
import ws.biotea.ld2rdf.rdf.model.ao.StartEndElementSelector;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.GenerateMD5;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

/**
 * @author leylajael
 *
 */
public class OffsetRangeTextSelectorOWL implements SelectorDAO<OffsetRangeTextSelector>{
	/**
	 * Constructor.
	 * @param model Ontology model.
	 */
	public OffsetRangeTextSelectorOWL() {
	}
	public Resource addSelector(OffsetRangeTextSelector selector, Model model, String baseURL) throws URISyntaxException {
		Property opType = model.getProperty(ResourceConfig.OP_RDF_TYPE);
		Property opOnResource = model.getProperty(Selector.SELECTOR_OP_ON_RESOURCE);
		Property dpOffset = model.getProperty(OffsetRangeTextSelector.OFFSET_RANGE_TEXT_SELECTOR_DP_OFFSET);
		Property dpRange = model.getProperty(OffsetRangeTextSelector.OFFSET_RANGE_TEXT_SELECTOR_DP_RANGE);
		Property opLocator = model.getProperty(StartEndElementSelector.START_END_ELEMENT_SELECTOR_DP_LOCATOR);
		
		if (selector.getId() == null) {
			selector.setId(
				GenerateMD5.getInstance().getMD5Hash(
					selector.getElementURI() + "_" + selector.getOffset() + "_" + 
					selector.getRange()
				) + "_" + selector.getSelector().replaceAll("[^A-Za-z0-9]", ""));
		}
		String resourceURI = baseURL + "Selector_" + selector.getId();
		selector.setUri(new URI(resourceURI));
		Resource selectorClass = model.createResource(OffsetRangeTextSelector.OFFSET_RANGE_TEXT_SELECTOR_CLASS);
		Resource selectorRes = model.createResource(selector.getUri().toString(), selectorClass);
		selectorRes.addProperty(opType, selectorClass);
		
		Resource resDocument = model.createResource(selector.getDocument().getUri().toString()); 
		selectorRes.addProperty(opOnResource, resDocument);
		
		selectorRes.addProperty(dpOffset, "" + selector.getOffset(), XSDDatatype.XSDint);
		selectorRes.addProperty(dpRange, "" + selector.getRange(), XSDDatatype.XSDint);
		
		Resource resLocator = model.createResource(selector.getElementURI());
		model.add(selectorRes, opLocator, resLocator);
		return selectorRes;	
	}
	/**
	 * Adds an offsetRangeTextSelector to the model.
	 * @param orTextSelector
	 * @return
	 * @throws URISyntaxException
	 */
	public Resource addSelector(OffsetRangeTextSelector selector, OntModel model) throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;	
	}
	
	public void deleteSelector(long id, JenaOWLModel owlModel) throws Exception {
		
	}
	public void deleteAnnotationSelector(long id, JenaOWLModel owlModel)
			throws Exception {

	}	
}
