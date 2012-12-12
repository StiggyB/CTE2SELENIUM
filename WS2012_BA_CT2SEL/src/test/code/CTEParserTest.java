package test.code;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import cte.CTEParser;

public class CTEParserTest {

    CTEParser cp;
    
    @Before
    public void setUp() throws Exception {
        cp = new CTEParser(new File("password.cte"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseDocument() {
        try {
            cp.parseDocument();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
