
package com.pbtd.manager.base.common.easyui;

import java.io.Serializable;

/**
 * 对应EasyUI Combobox组件选项Json模型
 *
 * @author huangdiwen
 */
public class ComboBoxOptionModel  implements Serializable 
{

    /**
     * 
     */
    private static final long serialVersionUID = 5479166604748914414L;

    private String value;

    private String text;

    private boolean selected;

    private String extraField1;

    private String extraField2;

    public ComboBoxOptionModel()
    {
        super();
    }

    public ComboBoxOptionModel(String value, String text)
    {
        super();
        this.value = value;
        this.text = text;
    }

    public ComboBoxOptionModel(String value, String text, boolean selected)
    {
        this.value = value;
        this.text = text;
        this.selected = selected;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getExtraField1()
    {
        return extraField1;
    }

    public void setExtraField1(String extraField1)
    {
        this.extraField1 = extraField1;
    }

    public String getExtraField2()
    {
        return extraField2;
    }

    public void setExtraField2(String extraField2)
    {
        this.extraField2 = extraField2;
    }
}
