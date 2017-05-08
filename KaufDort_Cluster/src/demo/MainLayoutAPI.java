package demo;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListitemRenderer;

/**
 * @author jumperchen
 *
 */
public interface MainLayoutAPI {
	public Category[] getCategories();
	public ListModel getSelectedModel();
	public ListitemRenderer getItemRenderer();
}