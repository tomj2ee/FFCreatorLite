package org.springbus.ff.node;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFBase;
import org.springbus.ff.core.FFCommand;
import org.springbus.ff.core.FFContext;

import java.util.ArrayList;
import java.util.List;

public class FFCon extends FFBase {

    protected List<FFNode> children;
    protected List<Object> filters;
    protected int vLength;
    protected int index;

    protected boolean hasInput;
    protected FFContext context;
    protected FFCommand  command;

    public FFCon(FFBaseConf conf) {
        super(conf);
        this.type = "con";
        this.children = new ArrayList<>();
        this.filters = new ArrayList<>();
        this.vLength = 0;
        this.hasInput = false;
        this.context = null;
        this.command = null;
        this.parent = null;
    }

    public void addChild(FFNode child) {
        if (child.hasInput) {
            int index = this.vLength++;
            child.index = index;
            if (this.context != null) {
                this.context.currentIndex = index;
            }
        }

        child.parent = this;
        this.children.add(child);
    }

    public void addChildAt(FFNode child, int index) {
        if (child.hasInput) {
            int curIndex = this.vLength++;
            child.index = curIndex;
            if (this.context != null) {
                this.context.currentIndex = curIndex;
            }
        }

        child.parent = this;
        this.children.set(index, child);
    }

    public void removeChild(FFNode child) {
        child.parent = null;
        //Utils.deleteArrayElement(this.children, child);
        children.remove(child);
    }

    public void swapChild(FFNode child1, FFNode child2) {
        int id1 = children.indexOf(child1);
        int id2 = children.indexOf(child2);
        // Utils.swapArrayElement(this.children, child1, child2);
        children.set(id1, child2);
        children.set(id2, child1);
    }

    public FFCommand toCommand() {
        return command;
    }

    public void destroy() {
        this.children.clear();
    }

}
