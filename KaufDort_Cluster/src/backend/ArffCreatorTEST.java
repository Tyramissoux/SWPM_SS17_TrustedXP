package backend;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;;

public class ArffCreatorTEST {
	String csv;
	ArffCreator arff;
	String arffString;
	String csv_test_pfad;
	String arff_test_pfad;
	String csv_test_endung;
	String arff_test_endung;
	
	@Test
	public void setUp(){
		csv = new String("C:\\Users\\Frederik\\Downloads\\eclipse\\workspace\\SPM_TestdatensatzKlein_2017.csv"); //hier muss eine Datei hinterlegt werden 
		csv_test_pfad = csv.substring(0, csv.lastIndexOf("."));
		arff = new ArffCreator(csv);
		arff_test_pfad = arff.getArffFilePath().substring(0, arff.getArffFilePath().lastIndexOf(".") );
		arffString = arff.getArffFilePath();
		System.out.println(arffString);
		assertTrue(csv.endsWith(".csv"));
		assertTrue(arffString.endsWith(".arff"));
	}
	
	@Test
	public void testArffCreator(){
		assertEquals(csv_test_pfad,arff_test_pfad);
	}
}
