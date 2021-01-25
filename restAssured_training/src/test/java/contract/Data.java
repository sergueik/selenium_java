package contract;

import java.util.List;

public class Data {
	private List<Item> items;
	private Number itemsPerPage;
	private Number startIndex;
	private Number totalItems;
	private String updated;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> data) {
		items = data;
	}

	public Number getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Number data) {
		itemsPerPage = data;
	}

	public Number getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Number data) {
		startIndex = data;
	}

	public Number getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Number data) {
		totalItems = data;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String data) {
		updated = data;
	}
}
