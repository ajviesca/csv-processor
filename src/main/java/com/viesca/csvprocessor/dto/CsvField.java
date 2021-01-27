package com.viesca.csvprocessor.dto;


import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class CsvField {

    @XmlAttribute(name = "name")
    private final String fieldName;

    @XmlValue
    private final String value;

    public CsvField(String fieldName, String value) {
        this.fieldName = StringUtils.trim(fieldName);
        this.value = StringUtils.trim(value);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }
}
