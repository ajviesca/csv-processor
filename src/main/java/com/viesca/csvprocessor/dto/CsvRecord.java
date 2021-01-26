package com.viesca.csvprocessor.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class CsvRecord {

    private final String sourceFile;
    private final int index;
    private final List<String> headers;
    private final List<String> values;
    private String formattedOutput;

    public CsvRecord(String sourceFile, int index, List<String> headers, List<String> values) {
        this.sourceFile = sourceFile;
        this.index = index;
        this.headers = headers;
        this.values = values;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public int getIndex() {
        return index;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getValues() {
        return values;
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
                .append("headers", headers)
                .append("values", values)
                .toString();
    }
}
