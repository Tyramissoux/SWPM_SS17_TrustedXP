package frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import backend.BackEndController;

@SuppressWarnings({ "serial", "rawtypes" })
public class SelectorVM extends GenericForwardComposer {

	@Wire
	Intbox intBoxCluster;
	
	protected Listbox listbox; // autowired
	private ListModelList list; // the model of the listbox

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<String> data = Arrays.asList((String[]) Sessions.getCurrent()
				.getAttribute("headerValues"));
		list = new ListModelList(data, true);
		list.setMultiple(true);
		
		listbox.setCheckmark(true);
		listbox.setModel(list);
		
	}

	public void onClick$btnGO(Event event)
	{
		//uploadedFilePath
		Set<Listitem> set = listbox.getSelectedItems();
		if (set.size() != 0) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			Sessions.getCurrent().setAttribute("selectedIndices", set);
			for(Listitem li: set){
				list.add(li.getIndex());
			}
			int cluster = intBoxCluster.getValue();
			
			new BackEndController(list, (String)Sessions.getCurrent()
					.getAttribute("uploadedFilePath"),cluster);
		} else
			System.out.println("Error");
	}

	private int convertValue(String in){
		try{
			return Integer.valueOf(in);
		}
		catch(NumberFormatException nfe){
			System.out.println("Error");
			return 0;
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
