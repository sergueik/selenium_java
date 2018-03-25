package org.henrrich.jpagefactory.example.todolist;

import java.util.List;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;

import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;
import com.github.sergueik.jprotractor.NgBy;

import org.openqa.selenium.WebElement;

/**
 * Tests of Angular Todo List http://jaykanakiya.com/demos/angular-js-todolist/
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class TodoListPage {
	private NgWebDriver _driver;

	public void setDriver(NgWebDriver driver) {
		this._driver = driver;
	}

	public NgWebDriver getDriver() {
		return _driver;
	}

	@FindAll({
			@FindBy(how = How.CSS_CONTAINING_TEXT, using = "span.todoName", text = "Angular-js") })
	private List<WebElement> _rows;

	public int countRows() {
		_rows.stream()
				.forEachOrdered(element -> System.err.println(element.getText()));
		return _rows.size();
	}
}
