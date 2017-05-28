package frontend;

/**
 * Datenstruktur fuer die Listbox der customerChoice.zul - ausbaufaehig bei
 * Bedarf
 * 
 * @author wooooot
 * 
 */
public class FeatureItem {
	private String feature;
	private String example;
	
	public FeatureItem(){
	}
	
	public void setFeature(String feature){
		this.feature =feature;
	}
	
	public String getFeature(){
		return feature;
	}
	
	public void setExample(String example){
		this.example =example;
	}
	
	public String getExample(){
		return example;
	}
	

}
