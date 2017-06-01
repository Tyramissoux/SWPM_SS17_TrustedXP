package backend;
import org.junit.Test;
import static org.junit.Assert.*;

public class FeatureTEST {
	public void setUp(){
		Feature testObjekt = new Feature("Futter",4,2);
		assertEquals(testObjekt.getFeatureName(),"Futter");
		assertEquals(testObjekt.getFeatureType(),4);
	}
}
