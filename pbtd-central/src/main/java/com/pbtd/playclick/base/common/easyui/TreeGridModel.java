package com.pbtd.playclick.base.common.easyui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.reflect.FieldUtils;

/**
 * 对应EasyUI treegrid组件Json模型
 *
 * @author huangdiwen
 */
public final class TreeGridModel<T> extends ArrayList<TreeGridRowModel> {

    private static final long serialVersionUID = 3075950705093768616L;

    public TreeGridModel() {
    }

    public TreeGridModel(List<T> rowObjs) {
        this(rowObjs, null);
    }

    public TreeGridModel(List<T> rowObjs, Comparator<T> comparator) {
        this(rowObjs, comparator, true);
    }

    public TreeGridModel(List<T> rowObjs, Comparator<T> comparator, boolean cascade) {
        if (comparator != null) {
            Collections.sort(rowObjs, comparator);
        }

        for (T obj : rowObjs) {
            try {
                TreeGridRowModel nodeModel = new TreeGridRowModel();
                nodeModel.setId((Integer) FieldUtils.readField(obj, "id", true));
                nodeModel.setText((String) FieldUtils.readField(obj, "name", true));
                nodeModel.setParent((Integer) FieldUtils.readField(obj, "parent", true));
                nodeModel.setObject(obj);
                super.add(nodeModel);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("将对象转成TreeNodeModel时候出现异常错误！", e);
            }
        }

        this.buildTreeGrid(cascade);
    }

    /**
     * 所有的节点除了根节点之外，都必须与父节点一并出现或一并隐藏
     */
    public TreeGridModel<T> buildTreeGrid() {
        return this.buildTreeGrid(true);
    }

    /**
     *
     * @param cascade <ul> <li>True:所有的节点除了根节点之外，都必须与父节点一并出现或一并隐藏</li>
     * <li>False: 当某一个节点的父节点被过滤隐藏时，此节点将被放置于第一层</li> </ul>
     * @return
     */
    public TreeGridModel<T> buildTreeGrid(boolean cascade) {
        Iterator<TreeGridRowModel> it = this.iterator();
        while (it.hasNext()) {
            TreeGridRowModel gridRowModel = it.next();
            TreeGridRowModel parentNode = this.findTreeGridRow(this, gridRowModel.getParent());
            if (parentNode != null) {
                parentNode.setState(TreeNodeModel.STATE_CLOSE);
                parentNode.getChildren().add(gridRowModel);
                it.remove();
            }
        }

        it = this.iterator();
        while (it.hasNext()) {
            TreeGridRowModel gridRowModel = it.next();
            if (gridRowModel.getParent() >= 0 && cascade) {
                it.remove();
            }
        }

        return this;
    }

    private TreeGridRowModel findTreeGridRow(List<TreeGridRowModel> treeNodes, int id) {
        for (TreeGridRowModel node : treeNodes) {
            if (node.getId() == id) {
                return node;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                TreeGridRowModel foundNode = findTreeGridRow(node.getChildren(), id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }

        return null;
    }

    public void setIconCls(String iconCls) {
        for (TreeGridRowModel rowModel : this) {
            rowModel.setIconCls(iconCls);
        }
    }

    public void open() {
        for (TreeGridRowModel rowModel : this) {
            rowModel.open();
        }
    }

    public void openAll() {
        for (TreeGridRowModel rowModel : this) {
            rowModel.openAll();
        }
    }
}
