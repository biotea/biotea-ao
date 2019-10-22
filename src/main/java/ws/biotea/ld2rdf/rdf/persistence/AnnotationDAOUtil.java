package ws.biotea.ld2rdf.rdf.persistence;

import ws.biotea.ld2rdf.rdf.persistence.ao.AnnotationMappingOWLDAO;
import ws.biotea.ld2rdf.rdf.persistence.ao.AnnotationOWLDAO;
import ws.biotea.ld2rdf.rdf.persistence.oa.AnnotationMappingOWLOA;
import ws.biotea.ld2rdf.rdf.persistence.oa.AnnotationOWLOA;
import ws.biotea.ld2rdf.util.ResourceConfig;

public class AnnotationDAOUtil {	
	/**
	 * Returns the appropriate DAO according to the base URL and the selected ontology.
	 * @param base
	 * @param onto
	 * @return
	 */
	public static AnnotationDAO getDAO(String base, ConstantConfig onto) {
		AnnotationDAO dao = null;
		if (onto == ConstantConfig.OA) {
			if (ResourceConfig.getUseBio2RDF(base) || (ResourceConfig.getMappingFile("").length() != 0)) {
				dao = new AnnotationMappingOWLOA();
			} else {
				dao = new AnnotationOWLOA();
			}  			
		} else {
			if (ResourceConfig.getUseBio2RDF(base) || (ResourceConfig.getMappingFile("").length() != 0)) {
				dao = new AnnotationMappingOWLDAO();
			} else {
				dao = new AnnotationOWLDAO();
			}    			
		}
		return dao;
	}
}
