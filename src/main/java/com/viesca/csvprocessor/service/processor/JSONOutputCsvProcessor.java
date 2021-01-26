package com.viesca.csvprocessor.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.stream.Collectors;

public class JSONOutputCsvProcessor extends CsvProcessor {

    public JSONOutputCsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        super(csvReader, csvRecordMapper);
    }

    public void process(String src, String target) {
        new JSONProcessor(src, target).process();
    }

    private class JSONProcessor extends Processor {

        public JSONProcessor(String src, String target) {
            super(src, target);
        }

        protected void generateStringData() {
            setDataStrings(CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .map(csvRecord -> {
                        try {
                            return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(csvRecord);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }

        @Override
        protected String getFileType() {
            return "json";
        }

    }

}
