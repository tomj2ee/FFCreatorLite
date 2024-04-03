package org.springbus.ff.animate;

import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;

public class AniFilter {
    private String name;
    private Filter filter;
    private String type;
    private FilterData data;
    private String showType;

    public AniFilter(String name, Filter filter,
                     String type,
                     String showType,
                     FilterData data) {
        this.name = name;
        this.filter = filter;
        this.type=type;
        this.data = data;
        this.showType = showType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FilterData getData() {
        return data;
    }

    public void setData(FilterData data) {
        this.data = data;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }



}