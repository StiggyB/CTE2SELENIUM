package tools.tree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.haw_hamburg.ti.tools.tree.Knot;
import de.haw_hamburg.ti.tools.tree.Tree;

public class TreeTest {

    static Tree<String> tree;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        tree = new Tree<>("Null", 0);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTree() {
        Knot<String> k = new Knot<String>("Eins", 1);
        tree.getRootNode().addChild(k);
        tree.getLastNodeByLevel(1).addChild("Zwei", 2);
        tree.getRootNode().addChild("Eins2", 1);
        tree.getLastNodeByLevel(1).addChild("Zwei2", 2);
        Knot<String> k2 = new Knot<String>("2", 2);
        k.addChild(k2);
        tree.getRootNode().print();
        System.out.println(tree.roadToKnot(k2));
        System.out.println(tree.getRootNode().getNodes());
    }

}
