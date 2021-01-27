package com.viesca.csvprocessor.dto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@XmlRootElement
public class CsvRecord {

    private String sourceFile;
    private int index;
    private List<RecordField> recordFields;
    private String formattedOutput;

    public CsvRecord() {
    }

    public CsvRecord(String sourceFile, int index, List<String> headers, List<String> values) {
        this.sourceFile = sourceFile;
        this.index = index;

        if (CollectionUtils.size(headers) != CollectionUtils.size(values)) {
            throw new IllegalArgumentException("mismatch header and values length");
        }

        recordFields = IntStream.range(0, headers.size())
                .mapToObj(i -> new RecordField(headers.get(i), values.get(i)))
                .collect(Collectors.toList());
    }

    @XmlTransient
    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @XmlTransient
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    public List<RecordField> getRecordFields() {
        return recordFields;
    }

    public void setRecordFields(List<RecordField> recordFields) {
        this.recordFields = recordFields;
    }

    @XmlTransient
    public String getFormattedOutput() {
        return formattedOutput;
    }

    public void setFormattedOutput(String formattedOutput) {
        this.formattedOutput = formattedOutput;
    }

    @XmlTransient
    public Map<String, String> getFieldValuesAsMap() {
        return recordFields
                .stream()
                .collect(Collectors.toMap(RecordField::getFieldName,
                        RecordField::getValue,
                        (k, v) -> {
                            throw new IllegalArgumentException(String.format("Duplicate key %s", k));
                        },
                        LinkedHashMap::new));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sourceFile", sourceFile)
                .append("index", index)
                .append("recordFields", recordFields)
                .append("formattedOutput", formattedOutput)
                .toString();
    }
}
