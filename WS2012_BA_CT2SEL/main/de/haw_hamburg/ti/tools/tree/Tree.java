package de.haw_hamburg.ti.tools.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Tree<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8848548819946750701L;
    private Knot<T>           rootNode;

    public Tree(T seed, int level) {
        rootNode = new Knot<T>(seed, level);
    }

    public Tree(Knot<T> rootNode) {
        this.rootNode = rootNode;
    }

    public Knot<T> getRootNode() {
        return rootNode;
    }

    /**
     * Gets the last Knot of the given Tree level
     * 
     * @param level
     * @return
     */
    public Knot<T> getLastNodeByLevel(int level) {
        Knot<T> k = null;
        for (Iterator<Knot<T>> i = this.rootNode.getNodes().iterator(); i
                .hasNext();) {
            Knot<T> knot = i.next();
            if (knot.getLevel() == level) {
                k = knot;
            }
        }
        return k;
    }

    public boolean containsValue(Object value) {
        ArrayList<Knot<T>> list = (ArrayList<Knot<T>>) this.rootNode
                .getNodes();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getContent().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Knot<T>> roadToKnot(Knot<T> k) {
        return roadToKnot(k, null);
    }

    private LinkedList<Knot<T>> roadToKnot(Knot<T> k, Knot<T> actKnot) {
        LinkedList<Knot<T>> ll = new LinkedList<>();
        if (actKnot == null) {
            if (k.equals(rootNode)) {
                ll.add(k);
            } else {
                ll.addAll(0, roadToKnot(k, rootNode));
            }
        } else {
            if (actKnot.hasChildren()) {
                for (Knot<T> rc : actKnot.getChilds()) {
                    if (rc.equals(k)) {
                        ll.add(rc);
                        ll.addAll(0, roadToKnot(actKnot, null));
                    } else {
                        ll.addAll(0, roadToKnot(k, rc));
                    }
                }
            }
        }
        return ll;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Tree [rootNode=" + rootNode + "]";
    }

}
