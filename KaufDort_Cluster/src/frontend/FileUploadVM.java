package frontend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import frontend.helper.CSVReader;
import frontend.helper.FeatureItem;
import frontend.helper.OldUploadItem;

public class FileUploadVM {

	// INFO: es handelt sich hier um MVVM

	private String filePath;
	private AMedia fileContent;
	private File saveFolder;
	private List<OldUploadItem> items;
	private int selected;

	

	public FileUploadVM() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		if (!mgmt.isAuthenticated()) {
			Executions.sendRedirect("login.zul");
		}
	}

	
	public List<OldUploadItem> getItems() {
		if (items == null) {
			StoreToDataBase tmp= new StoreToDataBase();
			items=tmp.daten.getDatabase();
		}
		return items;
	}

	@Command
	public void logOut() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		mgmt.logOff();
		Executions.sendRedirect("login.zul");
	}
	
	@Command
	public void loadOld() {
		Clients.showBusy("Bitte warten...");
		OldUploadItem tmp=items.get(selected);
		
		Sessions.getCurrent().setAttribute("finalClusterList", tmp.getClusterList());
		Sessions.getCurrent().setAttribute("finalFeatureList", tmp.getFeatureList());
		Sessions.getCurrent().setAttribute("chosenNumOfClusters", tmp.getClustersUsed());
		Sessions.getCurrent().setAttribute("originalFileName", "");
		Sessions.getCurrent().setAttribute("uploadDate", tmp.getDate());
		Clients.clearBusy();
		Executions.sendRedirect("clusteringOutput.zul");
		
	}
	@Command
	public void passwordChange() {
		Executions.sendRedirect("userManager.zul");
	}

	
	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getSelected() {
		return selected;
	}

	public File getSaveFolder() {
		return saveFolder;
	}

	public String getUploadedFilePath() {
		return filePath;
	}

	/**
	 * Erzeugt einen neues Dateiordnerobjekt mit einem auf der aktuellen Zeit
	 * basierenden Pfad - Schema: Jahr_Monat_Tag_Stunde_Minute_Sekunde Grund:
	 * Uploadkonflikte von vorneherein meiden
	 * 
	 * @return File
	 */
	private File createFolder() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		String tmpPath = getTemp();
		if (!tmpPath.endsWith(File.separatorChar + ""))
			tmpPath = tmpPath + File.separatorChar;

		String date = day + "." + month + "." + year + "-" + hour + ":" + min
				+ ":" + sec;
		Sessions.getCurrent().setAttribute("uploadDate", date);
		String filePath = tmpPath + year + "_" + month + "_" + day + "_" + hour
				+ "_" + min + "_" + sec + File.separatorChar;

		File f = new File(filePath);
		return f;
	}

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * Mit Hilfe dieser Methode können auch große Dateien hochgeladen werden
	 * 
	 * @param view
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Executions.getCurrent().getDesktop().getWebApp().getConfiguration()
				.setMaxUploadSize(10 * 1024);// for larger files
	}

	@Command
	@NotifyChange("fileuploaded")
	public void fileUpload(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
			throws IOException {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent == null) {
			Messagebox.show("Hochladen fehlgeschlagen", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Media media = upEvent.getMedia();

		if (!media.getName().toLowerCase().endsWith(".csv")) {
			Messagebox.show("Ausgewählte Datei ist keine *.csv", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		String originalName = media.getName();
		Sessions.getCurrent().setAttribute("originalFileName", originalName);

		saveFolder = createFolder();
		filePath = saveFolder.getAbsolutePath() + File.separatorChar
				+ originalName;

		try {
			Files.copy(new File(filePath), media.getStreamData());
			//filePath="myFolder/my.csv";
			ArrayList<FeatureItem> header = new CSVReader(filePath)
					.getFeatureExampleList();
			if (header.size() == 0) {
				Messagebox
						.show("CSV Datei ist fehlerhaft formattiert (ungleich viele Elemente pro Zeile) - bitte andere Datei reparieren und nochmals hochladen",
								"Warnung", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			Sessions.getCurrent().setAttribute("headerValues", header);
			Sessions.getCurrent().setAttribute("uploadedFilePath", filePath);
			if (header.size() != 0)
				Executions.sendRedirect("customerChoice.zul");
			else {
				Messagebox.show(
						"Hochgeladene Datei war nicht korrekt formatiert",
						"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static String getTemp() {
		return System.getProperty("java.io.tmpdir");
	}

}
