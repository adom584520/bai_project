package com.pbtd.playclick.base.common.easyui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.reflect.FieldUtils;

/**
 * 对应EasyUI Tree组件的Json模型
 *
 * @author huangdiwen
 */
public final class TreeModel extends ArrayList<TreeNodeModel> {

    private static final long serialVersionUID = 8927642766558019372L;

    public TreeModel() {
    }

    @SuppressWarnings("rawtypes")
    public TreeModel(List rowObjs) {
        this(rowObjs, null);
    }

    @SuppressWarnings("rawtypes")
    public TreeModel(List rowObjs, Comparator comparator) {
        this(rowObjs, null, true);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public TreeModel(List rowObjs, Comparator comparator, boolean cascade) {
        if (comparator != null) {
            Collections.sort(rowObjs, comparator);
        }

        for (Object obj : rowObjs) {
            try {
                TreeNodeModel nodeModel = new TreeNodeModel();
                nodeModel.setId((Integer) FieldUtils.readField(obj, "id", true));
                nodeModel.setText((String) FieldUtils.readField(obj, "name", true));
                nodeModel.setParent((Integer) FieldUtils.readField(obj, "parent", true));
                super.add(nodeModel);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("将对象转成TreeNodeModel时候出现异常错误！", e);
            }
        }

        this.buildTree(cascade);
    }

    /**
     * 所有的节点除了根节点之外，都必须与父节点一并出现或一并隐藏
     *
     */
    @SuppressWarnings("rawtypes")
    public TreeModel buildTree() {
        return this.buildTree(true);
    }

    /**
     *
     * @param cascade <ul> <li>True:所有的节点除了根节点之外，都必须与父节点一并出现或一并隐藏</li>
     * <li>False: 当某一个节点的父节点被过滤隐藏时，此节点将被放置于第一层</li> </ul>
     * @return
     */
    @SuppressWarnings("rawtypes")
    public TreeModel buildTree(boolean cascade) {
        Iterator<TreeNodeModel> it = this.iterator();
        while (it.hasNext()) {
            TreeNodeModel nodeModel = it.next();
            TreeNodeModel parentNode = this.findTreeNode(this, nodeModel.getParent());
            if (parentNode != null) {
                parentNode.setState(TreeNodeModel.STATE_CLOSE);
                parentNode.getChildren().add(nodeModel);
                it.remove();
            }
        }

        it = this.iterator();
        while (it.hasNext()) {
            TreeNodeModel nodeModel = it.next();
            if (nodeModel.getParent() >= 0 && cascade) {
                it.remove();
            }
        }

        return this;
    }

    private TreeNodeModel findTreeNode(List<TreeNodeModel> treeNodes, int id) {
        for (TreeNodeModel node : treeNodes) {
            if (node.getId() == id) {
                return node;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                TreeNodeModel foundNode = findTreeNode(node.getChildren(), id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }

        return null;
    }

    public void setIconCls(String iconCls) {
        for (TreeNodeModel nodeModel : this) {
            nodeModel.setIconCls(iconCls);
        }
    }

    public void open() {
        for (TreeNodeModel nodeModel : this) {
            nodeModel.open();
        }
    }

    public void openAll() {
        for (TreeNodeModel nodeModel : this) {
            nodeModel.openAll();
        }
    }
}
