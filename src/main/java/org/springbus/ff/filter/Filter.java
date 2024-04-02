package org.springbus.ff.filter;

import java.util.Map;

public class Filter {

    private String filter;
    private Map<String, Object> options;

    public Filter(String filter, Map<String, Object> options) {
        this.filter = filter;
        this.options = options;
    }

    public String getFilter() {
        return filter;
    }
}
