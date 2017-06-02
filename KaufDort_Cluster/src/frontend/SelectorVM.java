package frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import frontend.helper.FeatureItem;
import backend.BackEndController;

@SuppressWarnings({ "serial" })
public class SelectorVM extends SelectorComposer<Component> {

	@Wire
	Intbox intBoxCluster;
	private ListModel<FeatureItem> featureChoice;

	@Wire
	protected Listbox listbox;
	// private ListModelList list; // the model of the listbox

	@Wire
	private Window win;

	int chosenNumOfClusters;
	ArrayList<Integer> chosenListBoxIndices;
	boolean allFeaturesChosen;

	@SuppressWarnings("unchecked")
	public SelectorVM() {
		featureChoice = new ListModelList<FeatureItem>(
				(Collection<? extends FeatureItem>) Sessions.getCurrent()
						.getAttribute("headerValues"));

		((ListModelList<FeatureItem>) featureChoice).setMultiple(true);
	}

	/**
	 * Methode wird aufgerufen nachdem alle Komponenten in der
	 * customerChoice.zul initialisiert wurden - preselect funktioniert nicht
	 */
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		for(int i = 0; i< listbox.getItems().size();i++){
			listbox.getItems().get(i).setSelected(true);
		}
	}

	public ListModel<FeatureItem> getFeatureChoice() {
		return featureChoice;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public void doAfterCompose(Component comp) throws Exception {
	 * super.doAfterCompose(comp); List<String> data = Arrays.asList((String[])
	 * Sessions.getCurrent() .getAttribute("headerValues")); list = new
	 * ListModelList(data, true); list.setMultiple(true);
	 * 
	 * listbox.setCheckmark(true); listbox.setModel(list);
	 * 
	 * }
	 */
	@Listen("onClick  = #btnGO")
	public void checkValues(Event event) {

		if (!checkIntBox())
			return;
		if (!checkListBox())
			return;
		new BackEndController(chosenListBoxIndices, (String) Sessions
				.getCurrent().getAttribute("uploadedFilePath"),
				chosenNumOfClusters,allFeaturesChosen);
	}

	private boolean checkIntBox() {
		chosenNumOfClusters = intBoxCluster.getValue();
		if (chosenNumOfClusters > 1 && chosenNumOfClusters < 11)
			return true;
		else {
			Messagebox
					.show("Gewählte Anzahl an gewünschten Clustern muss zwischen 2 und 10 liegen",
							"Warnung", Messagebox.OK, Messagebox.INFORMATION);
			return false;
		}
	}

	private boolean checkListBox() {
		Set<Listitem> set = listbox.getSelectedItems();
		if(featureChoice.getSize() == set.size()){
			allFeaturesChosen = true;
			return true;
		}
		if (set.size() != 0) {
			chosenListBoxIndices = new ArrayList<Integer>();
			Sessions.getCurrent().setAttribute("selectedIndices", set);
			for (Listitem li : set) {
				chosenListBoxIndices.add(li.getIndex());
			}
			return true;
		} else {
			Messagebox
					.show("Kein Feature ausgewählt - mindestens ein Feature muss ausgewählt werden",
							"Warnung", Messagebox.OK, Messagebox.INFORMATION);

			return false;
		}
	}

	//

	/*
	 * <listbox id="box" multiple="true" checkmark="true"> <listhead>
	 * <listheader label="Übernehmen" /> <listheader label="Feature" />
	 * <listheader label="Typ" /> </listhead> <listfoot> <listfooter span="2"
	 * id="subtotalFooter"></listfooter> <listfooter> <button
	 * id="submitOrderBtn" label="OK" /> </listfooter> <listfooter> <button
	 * id="clearBtn" label="clear" /> </listfooter> </listfoot> </listbox>
	 */
}
