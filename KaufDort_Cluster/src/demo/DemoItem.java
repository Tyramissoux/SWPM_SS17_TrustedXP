package demo;

public class DemoItem implements java.io.Serializable {
	private String _id;
	private String _cateId;
	private String _icon;
	private String _file;
	private String _label;
	public DemoItem(String id, String cateId, String file, String icon, String label) {
		_id = id;
		_cateId = cateId;
		_icon = icon;
		_file = file;
		_label = label;
	}
	public String getId() {
		return _id;
	}
	public String getCateId() {
		return _cateId;
	}
	public String getIcon() {
		return _icon;
	}
	public String getFile() {
		return _file;
	}
	public String getLabel() {
		return _label;
	}	
	public String toString() {
		return "[DemoItem:" + _id +", "+_file+']';
	}
}
