package backend;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class ArffCreatorTEST {
	String csv;
	String arff;
	String csv_test_pfad;
	String arff_test_pfad;
	String csv_test_endung;
	String arff_test_endung;
	ArrayList <String> temp;
	
	public void setUp(){
		csv = new String("C:\\Users\\Frederik\\Downloads\\eclipse\\workspace\\SPM_TestdatensatzKlein_2017.csv"); //hier muss eine Datei hinterlegt werden 
		temp = (ArrayList<String>) Arrays.asList(csv.split("."));
		csv_test_pfad = temp.get(0);
		csv_test_endung = temp.get(1);
		arff = "";
	}
	
	public void testmagicWekaTranslation(){
		arff = new ArffCreator(csv).getArffFilePath();
		temp = (ArrayList<String>) Arrays.asList(csv.split("."));
		arff_test_pfad = temp.get(0);
		arff_test_endung = temp.get(1);
		assertEquals(csv_test_pfad,arff_test_pfad);
		assertEquals(csv_test_endung,"csv");
		assertEquals(arff_test_endung,"arff");
	}
}
