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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;

public class FileUploadVM {

	private String filePath;
	private AMedia fileContent;
	private File saveFolder;

	public File getSaveFolder() {
		return saveFolder;
	}

	public String getUploadedFilePath() {
		return filePath;
	}

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
		String filePath = tmpPath + year + "_" + month + "_" + day + "_" + hour+ "_"+ min
				+ "_" + sec+File.separatorChar;
		File f = new File(filePath);
		return f;
	}

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

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
		if (upEvent != null) {
			Media media = upEvent.getMedia();

			// String webAppPath =
			// Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");

			saveFolder = createFolder();
			filePath = saveFolder.getAbsolutePath()+File.separatorChar + media.getName();

			try {
				Files.copy(new File(filePath), media.getStreamData());
				String[] header = new CSVReader(filePath)
						.getHeaderElements();
				Sessions.getCurrent().setAttribute("headerValues", header);
				Sessions.getCurrent().setAttribute("uploadedFilePath",filePath);
				if (header.length != 0)
					Executions.sendRedirect("customerChoice.zul");
				else
					System.out
							.println("ERROR FileUpload - reading Header failed");
			} catch (Exception e) {

			}

		}
	}

	public static String getTemp() {
		return System.getProperty("java.io.tmpdir");
	}

	/*
	 * @Command
	 * 
	 * @NotifyChange("fileContent") public void showPDF() throws IOException {
	 * System.out.println("filePath='"+filePath+"'"); File f = new
	 * File(filePath); byte[] buffer = new byte[(int) f.length()];
	 * FileInputStream fs = new FileInputStream(f); fs.read(buffer); fs.close();
	 * ByteArrayInputStream is = new ByteArrayInputStream(buffer); String
	 * resultName = f.getName(); fileContent = new AMedia(resultName, "pdf",
	 * "application/pdf", is);
	 * 
	 * }
	 */
}
