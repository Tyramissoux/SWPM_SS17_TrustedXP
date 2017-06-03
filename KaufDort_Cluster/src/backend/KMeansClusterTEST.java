package backend;

import static org.junit.Assert.*;
import org.junit.Test;
import backend.KMeansCluster;

/**
 * 
 * @author Frederik Golchert
 *
 */
public class KMeansClusterTEST {
	KMeansCluster testObjekt1;
	KMeansCluster testObjekt2;

	@Test
	public void testKMeansCluster(){
		testObjekt1 = new KMeansCluster(3,3);
		testObjekt2 = new KMeansCluster(5,5);
		
		assertEquals(3,testObjekt1.getClusterNumber());
		assertEquals(5,testObjekt2.getClusterNumber());
		assertEquals(0,testObjekt1.getNumberOfInstancesAssignedToCluster());
		assertEquals(0,testObjekt2.getNumberOfInstancesAssignedToCluster());
	}
}
