package com.pbtd.playclick.base.common.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应EasyUI datagrid组件Json模型
 *
 * @author huangdiwen
 */
public final class DataGridModel<T> {

    private int total;
    private List<DataGridRowModel> rows = new ArrayList<DataGridRowModel>();

    public DataGridModel() {
    }

    public DataGridModel(List<T> rowObjs) {
        this(rowObjs, rowObjs.size());
    }

    public DataGridModel(List<T> rowObjs, int total) {
        this.setRowObjects(rowObjs);
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataGridRowModel> getRows() {
        return rows;
    }

    public void setRows(List<DataGridRowModel> rows) {
        this.rows = rows;
    }

    public void setRowObjects(List<T> rowObjs) {
        List<DataGridRowModel> list = new ArrayList<DataGridRowModel>();
        for (Object obj : rowObjs) {
            DataGridRowModel row = new DataGridRowModel();
            row.setObject(obj);
            list.add(row);
        }
        this.setRows(list);
    }

    public DataGridModel<T> addRow(DataGridRowModel row) {
        this.rows.add(row);
        return this;
    }

    public DataGridModel<T> addRowObject(Object obj) {
        DataGridRowModel row = new DataGridRowModel();
        row.setObject(obj);
        this.addRow(row);
        return this;
    }
}
