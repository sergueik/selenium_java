package org.henrrich.jpagefactory;

import com.jprotractor.NgWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by henrrich on 21/04/2016.
 */
public class JPageFactoryElementLocator extends DefaultElementLocator {

    public JPageFactoryElementLocator(SearchContext searchContext, Field field) {
        super(searchContext, field);
    }

    public JPageFactoryElementLocator(SearchContext searchContext, AbstractAnnotations annotations) {
        super(searchContext, annotations);
    }

    @Override
    public WebElement findElement() {

        WebElement element = super.findElement();
        if (element instanceof NgWebElement) {
            return ((NgWebElement) element).getWrappedElement();
        }
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        List<WebElement> elements = super.findElements();
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            if (element instanceof NgWebElement) {
                elements.add(i, ((NgWebElement) element).getWrappedElement());
            }
        }
        return elements;
    }
}
