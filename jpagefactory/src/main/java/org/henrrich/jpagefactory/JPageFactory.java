package org.henrrich.jpagefactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by henrrich on 25/05/2016.
 */
public class JPageFactory {

    public static void initElements(SearchContext searchContext, Channel channel, Object page) {
        PageFactory.initElements(new JPageFactoryFieldDecorator(new JPageFactoryElementLocatorFactory(searchContext, channel)), page);
    }

    public static void initWebElements(SearchContext searchContext, Object page) {
        initElements(searchContext, Channel.WEB, page);
    }

    public static void initMobileElements(SearchContext searchContext, Object page) {
        initElements(searchContext, Channel.MOBILE, page);
    }
}
