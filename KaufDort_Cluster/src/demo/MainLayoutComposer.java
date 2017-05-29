package demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import frontend.UserCredentialManager;

/**
 * @author jumperchen
 * 
 */
@SuppressWarnings({ "deprecation", "rawtypes" })
public class MainLayoutComposer extends GenericForwardComposer implements
        MainLayoutAPI {

    @SuppressWarnings("deprecation")
	private static final Log log = Log.lookup(MainLayoutComposer.class);
    Textbox searchBox;
    Listbox itemList;
    Include xcontents;
    Div header;
    Button _selected;

    public MainLayoutComposer() {
    }

    private Map getCategoryMap() {
        return DemoWebAppInit.getCateMap();
    }

    public void onCategorySelect(ForwardEvent event) {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem item = null;
        if (_selected != btn) {
            _selected = btn;
        } else {
            item = itemList.getSelectedItem();
        }
        String href = getCategory(_selected.getId()).getHref();
        if (href != null) {
            Executions.getCurrent().sendRedirect(href);
        } else {
            itemList.setModel(getSelectedModel());
            if (item != null) {
                itemList.renderAll();
                ((Listitem) itemList.getFellow(item.getId())).setSelected(true);
            }
        }
    }

    public void onBookmarkChange$main(BookmarkEvent event) {
        String id = event.getBookmark();
        if (id.length() > 0) {
            final DemoItem[] items = getItems();
            for (int i = 0; i < items.length; i++) {
                if (items[i].getId().equals(id)) {
                    _selected = (Button) self.getFellow(items[i].getCateId());
                    itemList.setModel(getSelectedModel());
                    itemList.renderAll();
                    Listitem item = ((Listitem) itemList.getFellow(id));
                    item.setSelected(true);
                    itemList.invalidate();
                    setSelectedCategory(item);
                    xcontents.setSrc(((DemoItem) item.getValue()).getFile());
                    item.focus();
                    return;
                }
            }
        }
    }

    public void onSelect$itemList(SelectEvent event) {
        Listitem item = itemList.getSelectedItem();

        if (item != null) {

            // sometimes the item is unloaded.
            if (!item.isLoaded()) {
                itemList.renderItem(item);
            }

            setSelectedCategory(item);
            xcontents.setSrc(((DemoItem) item.getValue()).getFile());
        }
    }

    public void onMainCreate(Event event) {
        final Execution exec = Executions.getCurrent();
        final String id = exec.getParameter("id");
        Listitem item = null;
        if (id != null) {
            try {
                final LinkedList list = new LinkedList();
                final DemoItem[] items = getItems();
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getId().equals(id)) {
                        list.add(items[i]);
                    }
                }
                if (!list.isEmpty()) {
                    itemList.setModel(new ListModelList(list));
                    itemList.renderAll();
                    item = (Listitem) self.getFellow(id);
                    setSelectedCategory(item);
                }
            } catch (ComponentNotFoundException ex) { // ignore
            }
        }

        if (item == null) {
            item = (Listitem) self.getFellow("g1");
            setSelectedCategory(item);
        }
        xcontents.setSrc(((DemoItem) item.getValue()).getFile());
        itemList.selectItem(item);
    }

    private void setSelectedCategory(Listitem item) {
        DemoItem di = (DemoItem) item.getValue();
        _selected = (Button) self.getFellow(di.getCateId());
        String deselect = _selected != null ? "jq('#" + _selected.getUuid()
                + "').addClass('demo-seld').siblings().removeClass('demo-seld');" : "";
        Clients.evalJavaScript(deselect);
        item.getDesktop().setBookmark(item.getId());
    }

    public void onCtrlKey$searchBox(KeyEvent event) {
        int keyCode = event.getKeyCode();
        List items = itemList.getItems();
        if (items.isEmpty()) {
            return;
        }
        Listitem item = null;
        switch (keyCode) {
            case 38: // UP
                item = itemList.getItemAtIndex(items.size() - 1);
                itemList.setSelectedItem(item);
                break;
            case 40: // DOWN
                item = itemList.getItemAtIndex(0);
                itemList.setSelectedItem(item);
                break;
        }
        if (item != null) {
            if (!item.isLoaded()) {
                itemList.renderItem(item);
            }
            setSelectedCategory(item);
            xcontents.setSrc(((DemoItem) item.getValue()).getFile());
            item.focus();
        }
    }

    public void onChanging$searchBox(InputEvent event) {
        String key = event.getValue();
        LinkedList item = new LinkedList();
        DemoItem[] items = getItems();

        if (key.trim().length() != 0) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].getLabel().toLowerCase().indexOf(key.toLowerCase()) != -1) {
                    item.add(items[i]);
                }
            }
            itemList.setModel(new ListModelList(item));
        } else {
            itemList.setModel(new ListModelList(items));
        }
        _selected = null;
    }

    private DemoItem[] getItems() {
        LinkedList items = new LinkedList();
        Category[] categories = getCategories();
        for (int i = 0; i < categories.length; i++) {
            items.addAll(categories[i].getItems());
        }
        return (DemoItem[]) items.toArray(new DemoItem[]{});
    }

    public Category[] getCategories() {
        return (Category[]) getCategoryMap().values().toArray(new Category[]{});
    }

    public ListitemRenderer getItemRenderer() {
        return _defRend;
    }
    private static final ListitemRenderer _defRend = new ItemRender();

    private static class ItemRender implements ListitemRenderer, java.io.Serializable {

        public void render(Listitem item, Object data) {
            DemoItem di = (DemoItem) data;
            Listcell lc = new Listcell();
            item.setValue(di);
            lc.setHeight("30px");
            lc.setImage(di.getIcon());
            item.setId(di.getId());
            lc.setLabel(di.getLabel());
            lc.setParent(item);
        }

		@Override
		public void render(Listitem arg0, Object arg1, int arg2)
				throws Exception {
			// TODO Auto-generated method stub
			
		}
    };

    private Category getCategory(String cateId) {
        return (Category) getCategoryMap().get(cateId);
    }

    public ListModel getSelectedModel() {
        Category cate = _selected == null ? getCategories()[0]
                : getCategory(_selected.getId());
        return new ListModelList(cate.getItems());
    }

    // Composer Implementation
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        System.out.println("do after compose main....");
        super.doAfterCompose(comp);

        if (!UserCredentialManager.getIntance(session).isAuthenticated()) {
            System.out.println("cehcking authentication....");
            execution.sendRedirect("login.zul");
        }else{
        Events.postEvent("onMainCreate", comp, null);

        }
    }
}
