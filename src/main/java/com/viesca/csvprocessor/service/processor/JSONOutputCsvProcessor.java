package com.viesca.csvprocessor.service.processor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.io.StringWriter;

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

        protected void formatOutput() {
            CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .forEach(csvRecord -> {
                        JsonFactory jsonFactory = new JsonFactory();
                        StringWriter writer = new StringWriter();

                        try (JsonGenerator generator = jsonFactory.createGenerator(writer)) {
                            generator.useDefaultPrettyPrinter();
                            generator.writeStartObject();

                            for (int i = 0; i < csvRecord.getHeaders().size(); i++) {
                                generator.writeFieldName(csvRecord.getHeaders().get(i).trim());
                                generator.writeString(csvRecord.getValues().get(i).trim());
                            }

                            generator.writeEndObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        csvRecord.setFormattedOutput(writer.toString());
                    });
        }

        @Override
        protected String getFileType() {
            return "json";
        }

    }

}
