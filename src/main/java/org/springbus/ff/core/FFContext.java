package org.springbus.ff.core;

import lombok.Data;

@Data
public class FFContext {
    public int currentIndex;
    public String input;
    public String output;
    public boolean inFilters;

    public FFContext() {
        this.input = null;
        this.output = null;
        this.inFilters = false;
    }
}
