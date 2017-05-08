package frontend;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;


public class FileUploadVM {

	private String filePath;
	private boolean fileuploaded = false;
	AMedia fileContent;
	//String logText ="Press the Upload Button to upload any PDF file. PDFmodifier will cover the unwanted area. After processing, the showPDF button will be active and you can save the modified PDF";


	
	
	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public boolean isFileuploaded() {
		return fileuploaded;
	}

	public void setFileuploaded(boolean fileuploaded) {
		this.fileuploaded = fileuploaded;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Executions.getCurrent().getDesktop().getWebApp().getConfiguration().setMaxUploadSize(10 * 1024);//for larger files
	}


	@Command
	@NotifyChange("fileuploaded")
	public void onUploadPDF(
			 @ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
				        throws IOException  {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			
	      
			String webAppPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
			System.out.println("webAppPath='"+webAppPath+"'");
	
			
			Calendar now = Calendar.getInstance();
	        int year = now.get(Calendar.YEAR);
	        int month = now.get(Calendar.MONTH);
	        int day = now.get(Calendar.DAY_OF_MONTH);
	        int min = now.get(Calendar.MINUTE);
			/*wir speichern die hochgeladene Datei 
			 * jetzt unter dem Systemspezifischen temp-Verzeichnis.
			 * und nicht mehr im Web-Root. Hintergrund: im Web-root des ProduktivServers 
			 * hat die App selbst keine Schreibrechte aus Sicherheitsgruenden.
			 */
	        String originalName = media.getName();
	      	        
			String fileName = originalName + "_no_copyright_"+year+"_"+month+"_"+day+"_"+min+".pdf";
			String tmpPath = getTemp();
			if (!tmpPath.endsWith(File.separatorChar+"")) tmpPath = tmpPath + File.separator;
			filePath = tmpPath + fileName;
			
			
			Files.copy(new File(filePath), media.getStreamData());
			//TO DO vom Benutzer ausgewählte Werte
			//BackEndController bec = new BackEndController(selectedIndices, filePath, chosenNumOfClusters);
			
			
			fileuploaded = true;
			
		}
	}
	
	  public static String getTemp()
	    {
	    	return System.getProperty("java.io.tmpdir");
	    }

	/*@Command
	@NotifyChange("fileContent")
	public void showPDF() throws IOException {
		System.out.println("filePath='"+filePath+"'");
		File f = new File(filePath);
		byte[] buffer = new byte[(int) f.length()];
		FileInputStream fs = new FileInputStream(f);
		fs.read(buffer);
		fs.close();
		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
		String resultName = f.getName();
		fileContent = new AMedia(resultName, "pdf", "application/pdf", is);

	}*/
}
