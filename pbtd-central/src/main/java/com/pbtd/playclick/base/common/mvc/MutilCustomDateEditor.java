package com.pbtd.playclick.base.common.mvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * 可支持多种日期格式
 * 
 * @author huangdiwen
 */
public class MutilCustomDateEditor extends PropertyEditorSupport {

    private List<DateFormat> dateFormats = new ArrayList<DateFormat>();

    public MutilCustomDateEditor(String... dateFormats) {
        for (String dateFormat : dateFormats) {
            this.dateFormats.add(new SimpleDateFormat(dateFormat));
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? this.dateFormats.get(0).format(value) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isBlank(text)) {
            setValue(null);
        } else {
            for (DateFormat dateFormat : this.dateFormats) {
                try {
                    setValue(dateFormat.parse(text));
                    break;
                } catch (ParseException e) {
                    if (this.dateFormats.indexOf(dateFormat) == this.dateFormats.size() - 1) {
                        throw new IllegalArgumentException("Could not parse date: " + e.getMessage(), e);
                    }
                }
            }
        }
    }
}
