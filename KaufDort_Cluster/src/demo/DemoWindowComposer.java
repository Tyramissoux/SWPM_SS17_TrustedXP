package demo;

import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jumperchen
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class DemoWindowComposer extends GenericForwardComposer {
	Window view;
	Tab demoView;
	Textbox codeView;
	Button reloadBtn;
	Button tryBtn;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		((Window)comp).setContentSclass("demo-main-cnt");
		((Window)comp).setSclass("demo-main");
		final Div inc = new Div();
		Executions.createComponents("/bar.zul", inc, null);
		inc.setStyle("float:right");
		comp.insertBefore(inc, comp.getFirstChild());
		if (view != null) execute();
	}
	public void execute() {
		Components.removeAllChildren(view);
		String code = codeView.getValue();
		try {
			if (tryBtn.isVisible())
				Executions.createComponentsDirectly(code, "zul", view, null);
			else 
				Executions.createComponents("/macros/warning.zul", view, null);
		} catch (RuntimeException e) {
			if ("true".equalsIgnoreCase(System.getProperty("zksandbox.debug")))
				System.out.println("\n Error caused by zksandbox at : " + new java.util.Date() + "\n code: " + code);
			throw e;
		}
	}
	public void onClick$reloadBtn(Event event) {
		demoView.setSelected(true);
		Path.getComponent("//userGuide/xcontents").invalidate();
	}
	public void onClick$tryBtn(Event event) {
		demoView.setSelected(true);
		execute();
	}
}
