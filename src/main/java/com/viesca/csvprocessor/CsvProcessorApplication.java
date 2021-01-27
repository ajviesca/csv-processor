package com.viesca.csvprocessor;

import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import com.viesca.csvprocessor.service.processor.JSONOutputCsvProcessor;
import com.viesca.csvprocessor.service.processor.XMLOutputCsvProcessor;
import com.viesca.csvprocessor.util.JSONWriter;
import org.apache.commons.lang3.StringUtils;

public class CsvProcessorApplication {

    private static final CsvReader CSV_READER = new CsvReader();
    private static final CsvRecordMapper CSV_RECORD_MAPPER = new CsvRecordMapper();
    private static final JSONWriter JSON_WRITER = new JSONWriter();
    private static final XMLOutputCsvProcessor xmlOutputCsvProcessor = new XMLOutputCsvProcessor(CSV_READER, CSV_RECORD_MAPPER);
    private static final JSONOutputCsvProcessor jsonOutputCsvProcessor = new JSONOutputCsvProcessor(CSV_READER, CSV_RECORD_MAPPER, JSON_WRITER);

    public static void main(String... args) {
        String mode = null;
        String srcDir = null;
        String targetDir = null;

        for (String arg : args) {
            if (arg.startsWith("-mode=")) {
                mode = arg.substring(arg.lastIndexOf("=") + 1, arg.length());
            }
            if (arg.startsWith("-srcDir=")) {
                srcDir = arg.substring(arg.lastIndexOf("=") + 1, arg.length());
            }
            if (arg.startsWith("-targetDir=")) {
                targetDir = arg.substring(arg.lastIndexOf("=") + 1, arg.length());
            }
        }

        if (StringUtils.isBlank(srcDir)) {
            throw new IllegalArgumentException("mode and source directory required");
        }
        if (StringUtils.isBlank(targetDir)) {
            targetDir = srcDir;
        }

        if ("json".equalsIgnoreCase(mode)) {
            System.out.println("JSON processing");
            jsonOutputCsvProcessor.process(srcDir, targetDir);
        } else if ("xml".equalsIgnoreCase(mode)) {
            System.out.println("XML processing");
            xmlOutputCsvProcessor.process(srcDir, targetDir);
        } else {
            System.out.println("no mode specified, generating all supported file types");
            System.out.println("JSON processing");
            jsonOutputCsvProcessor.process(srcDir, targetDir);
            System.out.println("XML processing");
            xmlOutputCsvProcessor.process(srcDir, targetDir);
        }
    }
}
