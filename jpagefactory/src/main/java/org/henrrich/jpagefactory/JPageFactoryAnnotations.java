package org.henrrich.jpagefactory;

import com.jprotractor.NgBy;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.henrrich.jpagefactory.annotations.FindBys;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.reflect.Field;

/**
 * Created by henrrich on 13/04/2016.
 */
public class JPageFactoryAnnotations extends Annotations {

    private boolean isWebChannel;

    public JPageFactoryAnnotations(Field field, Channel channel) {
        super(field);
        this.isWebChannel = Channel.WEB.equals(channel);
    }

    public By buildBy() {
        this.assertValidAnnotations();
        By ans = null;
        FindBys findBys = (FindBys) this.getField().getAnnotation(FindBys.class);
        if (findBys != null) {
            ans = this.buildByFromFindBys(findBys);
        }

        FindAll findAll = (FindAll) this.getField().getAnnotation(FindAll.class);
        if (ans == null && findAll != null) {
            ans = this.buildBysFromFindByOneOf(findAll);
        }

        FindBy findBy = (FindBy) this.getField().getAnnotation(FindBy.class);
        if (ans == null && findBy != null) {
            ans = this.buildByFromFindBy(findBy);
        }

        return ans;
    }

    protected By buildByFromFindBys(FindBys findBys) {
        FindBy[] findByArray = findBys.value();
        By[] byArray = new By[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            byArray[i] = this.buildByFromFindBy(findByArray[i]);
        }

        return new ByChained(byArray);
    }

    protected By buildBysFromFindByOneOf(FindAll findBys) {
        FindBy[] findByArray = findBys.value();
        By[] byArray = new By[findByArray.length];

        for(int i = 0; i < findByArray.length; ++i) {
            byArray[i] = this.buildByFromFindBy(findByArray[i]);
        }

        return new ByAll(byArray);
    }

    protected By buildByFromFindBy(FindBy findBy) {
        How how = getHowDefinition(findBy);
        String using = getUsingDefinition(findBy);

        if (using.isEmpty()) {
            return null;
        }

        switch (how) {
            case CLASS_NAME:
                return By.className(using);

            case CSS:
                return By.cssSelector(using);

            case ID:
            case UNSET:
                return By.id(using);

            case ID_OR_NAME:
                return new ByIdOrName(using);

            case LINK_TEXT:
                return By.linkText(using);

            case NAME:
                return By.name(using);

            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(using);

            case TAG_NAME:
                return By.tagName(using);

            case XPATH:
                return By.xpath(using);

            case BINDING:
                return NgBy.binding(using);

            case BUTTON_TEXT:
                return NgBy.buttonText(using);

            case PARTIAL_BUTTON_TEXT:
                return NgBy.partialButtonText(using);

            case MODEL:
                return NgBy.model(using);

            case INPUT:
                return NgBy.input(using);

            case REPEATER:
                return NgBy.repeater(using);

            case OPTIONS:
                return NgBy.options(using);

            case REPEATER_SELECTED_OPTION:
                return NgBy.selectedRepeaterOption(using);

            case SELECTED_OPTION:
                return NgBy.selectedOption(using);

            default:
                // Note that this shouldn't happen (eg, the above matches all
                // possible values for the How enum)
                throw new IllegalArgumentException("Cannot determine how to locate element ");
        }
    }

    private String getUsingDefinition(FindBy findBy) {
        String using = findBy.using();
        if (using.isEmpty()) {
            if (isWebChannel) {
                using = findBy.usingWeb();
            } else {
                using = findBy.usingMobile();
            }

        } else {
            if (!findBy.usingWeb().isEmpty() || !findBy.usingMobile().isEmpty()) {
                throw new IllegalArgumentException("If you use 'using' attribute, you must not also use 'usingWeb' and 'usingMobile' attributes");
            }
        }

        return using;
    }

    private How getHowDefinition(FindBy findBy) {
        How how = findBy.how();
        if (how.equals(How.UNSET)) {
            if (isWebChannel) {
                how = findBy.howWeb();
            } else {
                how = findBy.howMobile();
            }

        } else {
            if (!findBy.howWeb().equals(How.UNSET) || !findBy.howMobile().equals(How.UNSET)) {
                throw new IllegalArgumentException("If you use 'using' attribute, you must not also use 'usingWeb' and 'usingMobile' attributes");
            }
        }

        return how;
    }

    protected void assertValidAnnotations() {
        FindBys findBys = (FindBys)this.getField().getAnnotation(FindBys.class);
        FindAll findAll = (FindAll)this.getField().getAnnotation(FindAll.class);
        FindBy findBy = (FindBy)this.getField().getAnnotation(FindBy.class);
        if(findBys != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindBys\' annotation, you must not also use a \'@FindBy\' annotation");
        } else if(findAll != null && findBy != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBy\' annotation");
        } else if(findAll != null && findBys != null) {
            throw new IllegalArgumentException("If you use a \'@FindAll\' annotation, you must not also use a \'@FindBys\' annotation");
        }
    }
}
