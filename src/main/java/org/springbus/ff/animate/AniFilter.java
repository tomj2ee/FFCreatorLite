package org.springbus.ff.animate;

public class AniFilter {
    private String name;
    private String filter;
    private String type;
    private Object data;
    private String showType;

    public AniFilter(String name, String filter, String showType,
                     String type, Object data) {
        this.name = name;
        this.filter = filter;
        this.type = type;
        this.data = data;
        this.showType = showType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}