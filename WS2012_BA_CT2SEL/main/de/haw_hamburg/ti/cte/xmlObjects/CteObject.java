package de.haw_hamburg.ti.cte.xmlObjects;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CteObject implements Serializable {

    private static final long serialVersionUID = -9175571128726499585L;
    private String            id;
    private String            name;
    private Pattern           idPattern        = Pattern.compile("\\d+");

    public CteObject(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        Matcher idMatcher = idPattern.matcher(id);
        idMatcher.find();
        Integer intId;
        intId = Integer.parseInt(idMatcher.group());
        return intId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id + " name=" + name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CteObject other = (CteObject) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
