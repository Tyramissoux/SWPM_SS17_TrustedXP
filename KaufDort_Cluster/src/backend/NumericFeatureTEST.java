package backend;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class NumericFeatureTEST {

	NumericFeature testObjekt1;
	NumericFeature testObjekt2;
	ArrayList<HashMap<Double, Integer>> testList1;
	ArrayList<HashMap<Double, Integer>> testList2;
	
	
	@Before
	public void setUp(){
		testObjekt1 = new NumericFeature("Futter",4,6);
		testObjekt2 = new NumericFeature("Trinken",8,6);
		testList1 = new ArrayList<HashMap<Double, Integer>>();
		testList2 = new ArrayList<HashMap<Double, Integer>>();
		testObjekt1.addFeatureElementForCluster(1,1.1);
		testObjekt1.addFeatureElementForCluster(2,2.2);
		testObjekt1.addFeatureElementForCluster(3,3.3);
		testObjekt1.addFeatureElementForCluster(4,4.4);
		testObjekt1.addFeatureElementForCluster(5,5.5);
		testObjekt2.addFeatureElementForCluster(1,6.6);
		testObjekt2.addFeatureElementForCluster(2,7.7);
		testObjekt2.addFeatureElementForCluster(3,8.8);
		testObjekt2.addFeatureElementForCluster(4,9.9);
		testObjekt2.addFeatureElementForCluster(5,10.1);
		
		for (int i = 0; i < 6; i++) {
			testList1.add(new HashMap<Double, Integer>());
			testList2.add(new HashMap<Double, Integer>());
		}

		testList1.get(1).put(1.1, 1);
		testList1.get(2).put(2.2, 1);
		testList1.get(3).put(3.3, 1);
		testList1.get(4).put(4.4, 1);
		testList1.get(5).put(5.5, 1);
		testList2.get(1).put(6.6, 1);
		testList2.get(2).put(7.7, 1);
		testList2.get(3).put(8.8, 1);
		testList2.get(4).put(9.9, 1);
		testList2.get(5).put(10.1, 1);
	}
	
	
	@Test
	public void testNumericFeatureAttributes(){		
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
