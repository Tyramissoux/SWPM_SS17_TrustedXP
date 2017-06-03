package backend;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Frederik Golchert
 *
 */
public class FeatureTEST {
	@Test
	public void testFeature(){
		Feature testObjekt1 = new Feature("Futter",4,2);
		Feature testObjekt2 = new Feature("Trinken",8,5);
		assertEquals(testObjekt1.getFeatureName(),"Futter");
		assertEquals(testObjekt1.getFeatureType(),4);
		assertEquals(testObjekt2.getFeatureName(),"Trinken");
		assertEquals(testObjekt2.getFeatureType(),8);
	}
}
