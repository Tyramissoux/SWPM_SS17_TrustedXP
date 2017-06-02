package backend;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import backend.CSVRewriter;


public class CSVRewriterTEST {
	
	CSVRewriter testObjekt;
	String testAusgangsobjektPfad;
	String testErgebnisobjektPfad;
	String musterloesungPfad;
	BufferedReader readMusterloesung;
	BufferedReader readTestloesung;
	String testloesung;
	String musterloesung;
	String temp;
	
	@Before
	public void setUp() throws IOException{
		testAusgangsobjektPfad = "C:/Users/Frederik/Downloads/eclipse/workspace/SPM_TestdatensatzKlein_2017.csv"; //Muss vor dem Laufen des Tests geändert werden
		musterloesungPfad = "C:/Users/Frederik/Downloads/eclipse/workspace/Musterloesung.csv"; //Muss vor dem Laufen des Tests geändert werden
		
		testObjekt = new CSVRewriter(new ArrayList<Integer>(Arrays.asList(1, 2, 4, 7,9)),
				"C:/Users/Frederik/Downloads/eclipse/workspace/SPM_TestdatensatzKlein_2017.csv");
		testErgebnisobjektPfad = testObjekt.getGeneratedCSV();
		
		try {
			readTestloesung = createFileReader(testErgebnisobjektPfad);
			readMusterloesung = createFileReader(musterloesungPfad);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while((temp = readTestloesung.readLine()) != null)
			testloesung = testloesung + temp;
		
		while((temp = readMusterloesung.readLine()) != null)
			musterloesung = musterloesung + temp;
	}
	
	@After
	public void closeUp() throws IOException{
		readTestloesung.close();
		readMusterloesung.close();
	}
	
	@Test
	public void testCSVRewriter() throws IOException{
		assertEquals(musterloesung ,testloesung);
	}
	
	
	private BufferedReader createFileReader(String pfad) throws FileNotFoundException,	UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(
		pfad), "windows-1252"));
	}
}

