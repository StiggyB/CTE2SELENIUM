package de.haw_hamburg.ti.tools.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Knot<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6031630385191981131L;
    private List<Knot<T>> childs      = new ArrayList<Knot<T>>();
    private T             content;
    private int           level;
    private boolean       hasChildren = false;

    public Knot(T content, int level) {
        this.content = content;
        this.level = level;
    }

    public void addChild(Knot<T> child) {
        hasChildren = true;
        childs.add(child);
    }

    public List<Knot<T>> getChilds() {
        return childs;
    }

    public T getContent() {
        return content;
    }

    public int getLevel() {
        return level;
    }

    public void addChild(T next, int level) {
        hasChildren = true;
        childs.add(new Knot<T>(next, level));
    }

    public Knot<T> getChild(int i) {
        return getChilds().get(i);
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "|_ " : "|-") + content);
        if (childs != null) {
            for (int i = 0; i < childs.size() - 1; i++) {
                childs.get(i).print(prefix + (isTail ? "    " : "|   "),
                        false);
            }
            if (childs.size() >= 1) {
                childs.get(childs.size() - 1).print(
                        prefix + (isTail ? "    " : "|   "), true);
            }
        }
    }

    public ArrayList<Knot<T>> getNodes() {
        return getNodes(new ArrayList<Knot<T>>(), true);
    }

    private ArrayList<Knot<T>> getNodes(ArrayList<Knot<T>> list,
            boolean hasChildren) {
        list.add(this);
        if (childs != null) {
            for (int i = 0; i < childs.size() - 1; i++) {
                childs.get(i).getNodes(list, false);
            }
            if (childs.size() >= 1) {
                childs.get(childs.size() - 1).getNodes(list, true);
            }
        }
        return list;
    }
    
    /**
     * TODO LIST OVER CONTENT FOR FASTER ACCESS
     */
    public ArrayList<T> getContentList() {
        ArrayList<T> ret = new ArrayList<>();
        for (Knot<T> k : getNodes()) {
            ret.add(k.getContent());
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return "Node [content=" + content.toString() + "]";
    }

}
