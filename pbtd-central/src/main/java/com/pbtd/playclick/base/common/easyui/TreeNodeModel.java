package com.pbtd.playclick.base.common.easyui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对应EasyUI tree组件treenode的Json模型
 *
 * @author huangdiwen
 *
 */
public class TreeNodeModel {

    private int id;
    private String text;
    private String iconCls;
    private String state; // open or closed
    private boolean checked;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private List<TreeNodeModel> children = new ArrayList<TreeNodeModel>();
    private int parent = -1;
    public final static String STATE_OPEN = "open";
    public final static String STATE_CLOSE = "closed";

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    /**
     * @see STATE_OPEN
     * @see STATE_CLOSE
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeNodeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeModel> children) {
        this.children = children;
    }

    void open() {
        if (this.children.size() > 0) {
            this.state = STATE_OPEN;
        }
    }

    void openAll() {
        if (this.children.size() > 0) {
            this.state = STATE_OPEN;
            for (TreeNodeModel nodeModel : this.children) {
                nodeModel.openAll();
            }
        }
    }
}
