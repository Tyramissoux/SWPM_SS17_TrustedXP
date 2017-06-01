package backend;
import org.junit.Test;
import static org.junit.Assert.*;

public class NominalFeatureTEST {

	NominalFeature testObjekt;
	public void setUp(){
			testObjekt = new NominalFeature("Futter",4,6);
	}
	
	public void testpreFillNominalArrayList(){
		assertEquals(testObjekt.getFeatureName(),"Futter");
		assertEquals(testObjekt.getFeatureType(),4);
		assertEquals(testObjekt.getAllClusterData().size(),7);
	}
}
