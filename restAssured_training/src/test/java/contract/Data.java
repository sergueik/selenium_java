package contract;

import java.util.List;

public class Data {
	private List<Item> items;
	private Number itemsPerPage;
	private Number startIndex;
	private Number totalItems;
	private String updated;

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Number getItemsPerPage() {
		return this.itemsPerPage;
	}

	public void setItemsPerPage(Number itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public Number getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(Number startIndex) {
		this.startIndex = startIndex;
	}

	public Number getTotalItems() {
		return this.totalItems;
	}

	public void setTotalItems(Number totalItems) {
		this.totalItems = totalItems;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
