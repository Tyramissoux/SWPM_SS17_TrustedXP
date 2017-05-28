package frontend.helper;

public class OldUploadItem {
	
	//Basisklasse für Veränderungen
	
	private String _name;
	private int _price;
	private int _quantity;

	public OldUploadItem (String name, int price, int quantity) {
		setName (name);
		setPrice (price);
		setQuantity (quantity);
	}
	public void setName (String name) {
		_name = name;
	}
	public String getName () {
		return _name;
	}
	public void setPrice (int price) {
		_price = price;
	}
	public int getPrice () {
		return _price;
	}
	public void setQuantity (int quantity) {
		_quantity = quantity;
	}
	public int getQuantity () {
		return _quantity;
	}
}

