package com.viesca.csvprocessor.service.processor;

import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import com.viesca.csvprocessor.util.JSONWriter;
import org.apache.commons.collections4.CollectionUtils;

public class JSONOutputCsvProcessor extends CsvProcessor {

    private final JSONWriter jsonWriter;

    public JSONOutputCsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper, JSONWriter jsonWriter) {
        super(csvReader, csvRecordMapper);
        this.jsonWriter = jsonWriter;
    }

    public void process(String src, String target) {
        new JSONProcessor(src, target).process();
    }

    private class JSONProcessor extends Processor {

        public JSONProcessor(String src, String target) {
            super(src, target);
        }

        protected void formatOutput() {
            CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .forEach(csvRecord -> csvRecord.setFormattedOutput(jsonWriter.write(csvRecord.getCsvFields())));
        }

        @Override
        protected String getFileType() {
            return "json";
        }

    }

}
