package com.viesca.csvprocessor.service.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viesca.csvprocessor.dto.CsvRecord;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public abstract class CsvProcessor {

    private final CsvReader csvReader;
    private final CsvRecordMapper csvRecordMapper;

    public CsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        this.csvReader = csvReader;
        this.csvRecordMapper = csvRecordMapper;
    }

    public abstract void process(String src, String target);

    protected abstract class Processor {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private final String srcDir;
        private final String targetDir;

        private List<Path> sourceFiles;
        private List<CsvRecord> csvRecords;
        private List<String> dataStrings;

        protected Processor(String srcDir, String targetDir) {
            this.srcDir = srcDir;
            this.targetDir = targetDir;
        }

        protected void process() {
            createOutputDir();
            readFiles();
            mapToCsvRecord();
            generateStringData();
            writeToFile();
        }

        protected abstract void generateStringData();

        protected abstract String getFileType();

        protected void writeToFile() {
            if (CollectionUtils.isNotEmpty(getDataStrings())) {
                IntStream.range(0, getDataStrings().size())
                        .forEach(index -> {
                            try {
                                Files.write(Paths.get(getTargetDir(), String.format("%d.%s", index, getFileType())),
                                        getDataStrings().get(index).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }

        private void mapToCsvRecord() {
            csvRecords = csvRecordMapper.generateCsvRecords(sourceFiles);
        }

        private void readFiles() {
            sourceFiles = csvReader.getFiles(srcDir);
        }

        private void createOutputDir() {
            try {
                Files.createDirectories(Paths.get(targetDir).toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public List<CsvRecord> getCsvRecords() {
            return csvRecords;
        }

        public List<String> getDataStrings() {
            return dataStrings;
        }

        public void setDataStrings(List<String> dataStrings) {
            this.dataStrings = dataStrings;
        }

        public ObjectMapper getObjectMapper() {
            return objectMapper;
        }

        public String getTargetDir() {
            return targetDir;
        }
    }
}
