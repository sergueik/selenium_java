package testcases;

import javax.xml.XMLConstants;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

public class XMLValidationTest extends Paco {

    @Test
    @Fetch(url = "localhost/xml")
    public void can_validate_xml() throws Exception {
        page.get().validateXml("/xsds/test.xsd");
    }

    @Test
    @Fetch(url = "localhost/xml")
    public void can_validate_xml_without_namespace_awareness() throws Exception {
        page.get().validateXml("/xsds/test.xsd", false);
    }

    @Test
    @Fetch(url = "localhost/xml")
    public void can_validate_xml_with_namespace_choosen() throws Exception {
        page.get().validateXml("/xsds/test.xsd", false, XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }
}
