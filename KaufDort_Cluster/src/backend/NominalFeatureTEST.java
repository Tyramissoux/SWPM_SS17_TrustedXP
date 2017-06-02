package backend;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import backend.NominalFeature;

public class NominalFeatureTEST {

	NominalFeature testObjekt1;
	NominalFeature testObjekt2;
	ArrayList<HashMap<String, Integer>> testList1;
	ArrayList<HashMap<String, Integer>> testList2;
	
	
	@Before
	public void setUp(){
		testObjekt1 = new NominalFeature("Futter",4,6);
		testObjekt2 = new NominalFeature("Trinken",8,6);
		testList1 = new ArrayList<HashMap<String, Integer>>();
		testList2 = new ArrayList<HashMap<String, Integer>>();
		testObjekt1.addFeatureElementForCluster(1,"Ente");
		testObjekt1.addFeatureElementForCluster(2,"Gans");
		testObjekt1.addFeatureElementForCluster(3,"Pferd");
		testObjekt1.addFeatureElementForCluster(4,"Kuh");
		testObjekt1.addFeatureElementForCluster(5,"Schwein");
		testObjekt2.addFeatureElementForCluster(1,"Eichhörnchen");
		testObjekt2.addFeatureElementForCluster(2,"Huhn");
		testObjekt2.addFeatureElementForCluster(3,"Fasan");
		testObjekt2.addFeatureElementForCluster(4,"Ziege");
		testObjekt2.addFeatureElementForCluster(5,"Reh");
		
		for (int i = 0; i < 6; i++) {
			testList1.add(new HashMap<String, Integer>());
			testList2.add(new HashMap<String, Integer>());
		}

		testList1.get(1).put("Ente", 1);
		testList1.get(2).put("Gans", 1);
		testList1.get(3).put("Pferd", 1);
		testList1.get(4).put("Kuh", 1);
		testList1.get(5).put("Schwein", 1);
		testList2.get(1).put("Eichhörnchen", 1);
		testList2.get(2).put("Huhn", 1);
		testList2.get(3).put("Fasan", 1);
		testList2.get(4).put("Ziege", 1);
		testList2.get(5).put("Reh", 1);
	}
	
	
	@Test
	public void testNominalFeatureAttributes(){		
		assertEquals("Futter",testObjekt1.getFeatureName());
		assertEquals(4,testObjekt1.getFeatureType());
		assertEquals(6,testObjekt1.getAllClusterData().size());
		assertEquals("Trinken",testObjekt2.getFeatureName());
		assertEquals(8,testObjekt2.getFeatureType());
		assertEquals(6,testObjekt2.getAllClusterData().size());
	}
	
	@Test
	public void testaddFeatureElementForCluster(){
		assertEquals(testList1,testObjekt1.getAllClusterData());
		assertEquals(testList2,testObjekt2.getAllClusterData());
		
	}
}
