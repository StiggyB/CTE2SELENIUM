package de.haw_hamburg.ti.cte;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

public class CteObjectsInDocument implements NodeFilter {

    public short acceptNode(Node n) {
        if (n.getNodeType() == Node.ELEMENT_NODE) {

            for (CteObj co : CteObj.values()) {
                if (n.getNodeName().equalsIgnoreCase(co.name()))
                    return FILTER_ACCEPT;
            }

            if (n.getNodeName().equalsIgnoreCase("Tree")
                    || n.getNodeName().equalsIgnoreCase("Tag")
                    || n.getNodeName().equalsIgnoreCase("TestGroup")
                    || n.getNodeName().equalsIgnoreCase("Content")
                    || n.getNodeName().equalsIgnoreCase("CteObject"))
                return FILTER_SKIP;

        }

        return FILTER_REJECT;
    }
}
