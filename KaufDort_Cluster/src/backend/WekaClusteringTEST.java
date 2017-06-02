package backend;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import backend.WekaClustering;

public class WekaClusteringTEST {

	WekaClustering testObjekt;
	String testObjektPfad;
	BufferedReader read;
	String temp;
	String ergebnis;
	String[] ergebnisString;
	ArrayList <String> testFeatures;
	ArrayList <String> testObjektFeatures;
	
	@Before
	public void setUp() throws IOException{
		testObjektPfad = "C:/Users/Frederik/Downloads/eclipse/workspace/MusterloesungWeka.csv"; //testdateien müssen eingefügt werden
		testObjekt = new WekaClustering("C:/Users/Frederik/Downloads/eclipse/workspace/MusterloesungWeka.arff",5);
		read = createFileReader(testObjektPfad);
		ergebnis = "";
		testFeatures = new ArrayList<String>();
		testObjektFeatures = new ArrayList<String>();
		
		while((temp = read.readLine()) != null)
			ergebnis = ergebnis + temp;
		ergebnisString = ergebnis.split(",");
		System.out.println(ergebnisString[23]);
		
		for(int i=0;i<testFeatures.size();i++)
			testFeatures.add(ergebnisString[i]);
		
		for(int i=0;i<testFeatures.size();i++)
			testObjektFeatures.add(testObjekt.getFeatureList().get(i).getFeatureName());
	}
	
	@Test
	public void testWekaClustering(){
		assertEquals(testFeatures,testObjektFeatures);
	}
	
	
	private BufferedReader createFileReader(String pfad) throws FileNotFoundException,	UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(
		pfad), "windows-1252"));
	}
	
}
