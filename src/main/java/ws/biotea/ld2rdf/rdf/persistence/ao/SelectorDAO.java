package ws.biotea.ld2rdf.rdf.persistence.ao;

import java.net.URISyntaxException;

import ws.biotea.ld2rdf.rdf.model.ao.Selector;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

public interface SelectorDAO<T extends Selector> {
	public Resource addSelector(T selector, OntModel model) throws URISyntaxException;
	public Resource addSelector(T selector, Model model) throws URISyntaxException;
	public void deleteSelector(long id, JenaOWLModel owlModel) throws Exception;
	public void deleteAnnotationSelector(long id, JenaOWLModel owlModel) throws Exception;
}
