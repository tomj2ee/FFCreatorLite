package org.springbus.ff.filter;

import java.util.Map;

public class Filter {

    public String outputs;
    private String filter;
    private Map<String, Object> options;

    public Filter(String filter, Map<String, Object> options) {
        this.filter = filter;
        this.options = options;
    }

    public String getFilter() {
        return filter;
    }
    public Map<String, Object> getOptions(){
        return options;
    }
    public String  getOptionKey(String k){
        return options.get(k)+"";
    }
}
