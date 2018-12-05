package com.pbtd.manager.base.common.easyui;

import java.util.ArrayList;

/**
 * 对应EasyUI Combobox组件的Json模型
 *
 * @author huangdiwen
 */
public final class ComboBoxModel extends ArrayList<ComboBoxOptionModel> {

    private static final long serialVersionUID = -1893144289266657883L;

    public ComboBoxModel addOption(ComboBoxOptionModel option) {
        super.add(option);
        return this;
    }
}
