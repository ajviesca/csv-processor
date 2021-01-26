package com.viesca.csvprocessor;

import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import com.viesca.csvprocessor.service.processor.JSONOutputCsvProcessor;
import com.viesca.csvprocessor.service.processor.XMLOutputCsvProcessor;
import org.apache.commons.lang3.StringUtils;

public class CsvProcessorApplication {

    private static final CsvReader CSV_READER = new CsvReader();
    private static final CsvRecordMapper CSV_RECORD_MAPPER = new CsvRecordMapper();

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

        if (StringUtils.isBlank(mode) || StringUtils.isBlank(srcDir)) {
            throw new IllegalArgumentException("mode and source directory required");
        }
        if (StringUtils.isBlank(targetDir)) {
            targetDir = srcDir;
        }

        if ("json".equalsIgnoreCase(mode)) {
            System.out.println("JSON processing");
            new JSONOutputCsvProcessor(CSV_READER, CSV_RECORD_MAPPER).process(srcDir, targetDir);
        } else if ("xml".equalsIgnoreCase(mode)) {
            System.out.println("XML processing");
            new XMLOutputCsvProcessor(CSV_READER, CSV_RECORD_MAPPER).process(srcDir, targetDir);
        } else {
            System.out.println("unsupported mode " + mode);
        }
    }
}
