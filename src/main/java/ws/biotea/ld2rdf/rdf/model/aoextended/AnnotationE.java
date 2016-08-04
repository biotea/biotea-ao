/**
 * 
 */
package ws.biotea.ld2rdf.rdf.model.aoextended;

import ws.biotea.ld2rdf.rdf.model.ao.Annotation;

/**
 * @author leylajael
 *
 */
public class AnnotationE extends Annotation {
	private static final long serialVersionUID = 1L;
	private Integer frequency;
	private Double idf;
	/**
	 * Score for this annotation (usually for non-dictionary annotators)
	 */
	private Double score = null;

	public AnnotationE() {
		super();
	}

	/**
	 * Returns the frequency.
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * Sets the value for the frequency.
	 * @param frequence the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the idf
	 */
	public Double getIDF() {
		return idf;
	}

	/**
	 * @param idf the idf to set
	 */
	public void setIDF(double idf) {
		this.idf = idf;
	}
	

	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		int tempTF = this.frequency != null ? this.frequency : -1;
		double tempIDF = this.idf != null ? this.idf : -1;
		return "[\\'" + this.getId() + "\\'" +
			"; \\'" + this.getNodeId() + "\\'" +
			"; \\'" + this.getBodies() + "\\'" +
			"; \\'" + this.getTopics() + "\\'" +
			"; \\'" + tempTF + "\\'" + 
			"; \\'" + tempIDF  + "\\'" + "]\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AnnotationE))
			return false;
		return super.equals(obj);
	}
	
	
}
