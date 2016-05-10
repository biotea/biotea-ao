/**
 * 
 */
package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;



import ws.biotea.ld2rdf.rdf.model.ao.Selector;
import ws.biotea.ld2rdf.rdf.model.ao.XPointerSelector;
import ws.biotea.ld2rdf.util.ResourceConfig;
import ws.biotea.ld2rdf.util.annotation.AnnotationOntologyPrefix;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;

/**
 * @author leylajael
 *
 */
public class XPointerSelectorOWL implements SelectorDAO<XPointerSelector>{
	/**
	 * Constructor.
	 * @param model Ontology model.
	 */
	public XPointerSelectorOWL() {
	}
	/**
	 * Adds an offsetRangeTextSelector to the model.
	 * @param orTextSelector
	 * @return
	 * @throws URISyntaxException
	 */
	public Resource addSelector(XPointerSelector xpointerSelector, OntModel model) throws URISyntaxException {
		OntClass selectorClass = model.getOntClass(XPointerSelector.XPOINTER_SELECTOR_CLASS);
		Property opOnDocument = model.getProperty(Selector.SELECTOR_OP_ON_RESOURCE);
		Property dpXpointer = model.getProperty(XPointerSelector.XPOINTER_SELECTOR_DP_XPOINTER);
		
		//Selector
		xpointerSelector.setId(new Date().getTime() + "");
		String resourceURI = ResourceConfig.BIOTEA_URL + "XPointerSelector_" + xpointerSelector.getId();
		xpointerSelector.setUri(new URI(resourceURI));
		Resource selectorRes = model.createIndividual(resourceURI, selectorClass);
		//document
		selectorRes.addProperty(opOnDocument, xpointerSelector.getDocument().getUri().toString());
		//xpointer
		selectorRes.addLiteral(dpXpointer, xpointerSelector.getSelector());
		return (selectorRes);
	}
	public void deleteSelector(long id, JenaOWLModel owlModel) throws Exception {
		
	}
	public void deleteAnnotationSelector(long id, JenaOWLModel owlModel) throws Exception {
		OntModel model = owlModel.getOntModel();
		
		String queryString = 
				AnnotationOntologyPrefix.prefixes_AO() +
			" SELECT ?selector  " +
			" WHERE {" +
			" ?selector a " + AnnotationOntologyPrefix.AO_SELECTORS.getNS() + ":XPointerSelector . " +
			" ?selector " + AnnotationOntologyPrefix.AO_CORE.getNS() + ":xpointer 'ld:tag id=\"" + id + "\"' . " +   
			" } "; 
		//System.out.println("BIOTEA - deleteAnnotationSelector " + queryString);
		
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet rs = qe.execSelect();
		
		// Output query results	
		//ResultSetFormatter.out(System.out, rs, query);
		while (rs.hasNext()) {
			QuerySolution qs = rs.nextSolution(); //id, creator annotation <>, date
			String uri = qs.getResource("selector").getURI();
			OWLIndividual ind = owlModel.getOWLIndividual(uri);
			ind.delete();
		}
		// Important - free up resources used running the query
		qe.close();
	}
	public Resource addSelector(XPointerSelector selector, Model model) throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}
}
