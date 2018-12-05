package com.pbtd.playclick.base.common.easyui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对应EasyUI treegrid组件Json模型
 *
 * @author huangdiwen
 */
public class TreeGridRowModel extends DataGridRowModel {

    private static final long serialVersionUID = 4569625418911169942L;
    public final static String STATE_OPEN = "open";
    public final static String STATE_CLOSE = "closed";

    public TreeGridRowModel() {
    }

    public TreeGridRowModel(Object obj) {
        super.setObject(obj);
    }

    public int getId() {
        Object value = super.get("id");
        return value == null ? null : (Integer) value;
    }

    public void setId(int id) {
        super.put("id", id);
    }

    public String getText() {
        Object value = super.get("text");
        return value == null ? null : (String) value;
    }

    public void setText(String text) {
        super.put("text", text);
    }

    public String getIconCls() {
        Object value = super.get("iconCls");
        return value == null ? null : (String) value;
    }

    public void setIconCls(String iconCls) {
        super.put("iconCls", iconCls);
    }

    public String getState() {
        Object value = super.get("state");
        return value == null ? null : (String) value;
    }

    public void setState(String state) {
        super.put("state", state);
    }

    public boolean isChecked() {
        Object value = super.get("checked");
        return value == null ? null : (Boolean) value;
    }

    public void setChecked(boolean checked) {
        super.put("checked", checked);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAttributes() {
        Object value = super.get("attributes");
        if (value == null) {
            this.setAttributes(new HashMap<String, Object>());
            return this.getAttributes();
        } else {
            return (Map<String, Object>) value;
        }
    }

    public void setAttributes(Map<String, Object> attributes) {
        super.put("attributes", attributes);
    }

    @SuppressWarnings("unchecked")
    public List<TreeGridRowModel> getChildren() {
        Object value = super.get("children");
        if (value == null) {
            this.setChildren(new ArrayList<TreeGridRowModel>());
            return this.getChildren();
        } else {
            return (List<TreeGridRowModel>) value;
        }
    }

    public void setChildren(List<TreeGridRowModel> children) {
        super.put("children", children);
    }

    public int getParent() {
        Object value = super.get("parent");
        return value == null ? -1 : (Integer) value;
    }

    public void setParent(int parent) {
        super.put("parent", parent);
    }

    void open() {
        if (this.getChildren().size() > 0) {
            this.setState(STATE_OPEN);
        }
    }

    void openAll() {
        if (this.getChildren().size() > 0) {
            this.setState(STATE_OPEN);
            for (TreeGridRowModel rowModel : this.getChildren()) {
                rowModel.openAll();
            }
        }
    }
}
