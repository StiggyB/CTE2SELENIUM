package tools;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

import de.haw_hamburg.ti.tools.XMLParser;



public class XMLParserTest {

	Element root;
	
	@Before
	public void setUp() throws Exception {
		root = XMLParser.parse(new File("password.cte"));
	}
	
	@Test
	public void testParse() {
		try {
			assertThat(XMLParser.parse(new File("password.cte")), isA(Element.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	@Test
	public void testGetElements() {
		for (Iterator<Element> iterator = root.getElements().iterator(); iterator
				.hasNext();) {
			Element type = iterator.next();
			assertThat(type, is(Element.class));
			System.out.println("testGetElements()->Element: "
					+ type.getNodeName());
			// get only needed nodes, nodeType "1" means Element Node
			for (int i = 0; i < type.getChildNodes().getLength(); i++) {
				if (type.getChildNodes().item(i).getNodeType() == 1) {
					System.out.println("testGetElements()->Child: "
							+ type.getChildNodes().item(i).getNodeName() + " " + type.getChildNodes().item(i).getAttributes().item(0));
					for (int k = 0; k < type.getChildNodes().item(i)
							.getChildNodes().getLength(); k++) {
						if (type.getChildNodes().item(i).getChildNodes().item(k).getNodeType() == 1) {
							System.out.println("testGetElements()->Attr: "
									+ type.getChildNodes().item(i)
											.getChildNodes().item(k)
											.getNodeName());
						}
					}
				}
			}
		}
	}

	@Test
	public void testGetElements2() {
		for (Iterator<Element> iterator = xp.getElements().iterator(); iterator
				.hasNext();) {
			Element type = iterator.next();
			NodeList comp_nl = type.getElementsByTagName("Composition");
			if (comp_nl != null && comp_nl.getLength() > 0) {
				for (int i = 0; i < comp_nl.getLength(); i++) {

					// get the cte element
					Element el = (Element) comp_nl.item(i);
					// get the cte object
					System.out.println("getElements().getByName.getnodename: "
							+ el.getAttributes().item(0));
				}
			}
		}
	}

	@Test
	public void testGetElementAttributes() {
		for (Iterator<NamedNodeMap> iterator = xp.getElementAttributes()
				.iterator(); iterator.hasNext();) {
			NamedNodeMap type = iterator.next();
			assertThat(type, is(NamedNodeMap.class));
			for (int j = 0; j < type.getLength(); j++) {
				System.out
						.println("ACTUAL ATTRIBUTE(" + j + ")" + type.item(j));
			}
		}
	}
*/
}
