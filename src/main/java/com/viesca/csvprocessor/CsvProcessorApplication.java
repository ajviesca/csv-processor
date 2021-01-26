package com.viesca.csvprocessor;

import com.viesca.csvprocessor.service.processor.JSONOutputCsvProcessor;
import org.apache.commons.lang3.StringUtils;

public class CsvProcessorApplication {

    public static void main(String... args) {
        System.out.println("csv processor greets you.");

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

        if(StringUtils.isBlank(targetDir)) {
            targetDir = srcDir;
        }

        if ("json".equalsIgnoreCase(mode)) {
            System.out.println("JSON processing");
            new JSONOutputCsvProcessor().generateJSONFiles(srcDir, targetDir);
        } else {
            System.out.println("unsupported mode " + mode);
        }
    }
}
