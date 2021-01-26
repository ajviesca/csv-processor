package com.viesca.csvprocessor.service.processor;

import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;

public class XMLOutputCsvProcessor extends CsvProcessor {

    public XMLOutputCsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        super(csvReader, csvRecordMapper);
    }

    @Override
    public void process(String src, String target) {
        new XMLProcessor(src, target).process();
    }

    private class XMLProcessor extends Processor {

        protected XMLProcessor(String srcDir, String targetDir) {
            super(srcDir, targetDir);
        }

        @Override
        protected void formatOutput() {
            // TODO
        }

        @Override
        protected String getFileType() {
            return "xml";
        }

    }
}
