package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Feature ist eine Datenstruktur, die Daten zu einem einzelnen Feature pro
 * Cluster speichert und das Vorkommen der Werte, die das Feature annehmen kann,
 * berechnet.
 * 
 * @author Insa Kruse
 * 
 */
public class Feature implements Serializable{
	private String featureName;
	private int featureType;
	
	/**
	 * Konstruktor nimmt den Namen des betreffenden Features, den Featuretyp
	 * (von weka) und die Anzahl an Clustern, die fuer das Clustering verwendet
	 * wurden
	 * 
	 * @param featureName
	 * @param featureType
	 * @param numOfClusters
	 */
	public Feature(String featureName, int featureType, int numOfClusters) {
		super();
		
		
		this.featureName = featureName;
		this.featureType = featureType;
	}

	/**
	 * Gibt den Namen des Features zurueck
	 * 
	 * @return
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * Gibt den Typ des Features als Zahlwert zurueck - 0: numerisch, 1: nominal, 2: string,
	 * 3: datum, 4: relational - von weka festgelegt
	 * 
	 * @return
	 */
	public int getFeatureType() {
		return featureType;
	}

	/**
	 * Übersetzt den von Weka gegebenen Zahlenwert in einen String
	 * 
	 * @return Typ des Features
	 */
	/*
	 * private String featureType() { switch (featureType) { case 0: return
	 * "numeric"; case 1: return "nominal"; case 2: return "string"; case 3:
	 * return "date"; case 4: return "relational"; default: return null; } }
	 */

}
