package com.viesca.csvprocessor.dto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
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

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

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

    public String getFormattedOutput() {
        return formattedOutput;
    }

    public void setFormattedOutput(String formattedOutput) {
        this.formattedOutput = formattedOutput;
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
