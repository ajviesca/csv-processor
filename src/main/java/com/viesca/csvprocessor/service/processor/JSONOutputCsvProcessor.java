package com.viesca.csvprocessor.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JSONOutputCsvProcessor {

    private final CsvReader csvReader;
    private final CsvRecordMapper csvRecordMapper;

    public JSONOutputCsvProcessor() {
        this.csvReader = new CsvReader();
        this.csvRecordMapper = new CsvRecordMapper();
    }

    public JSONOutputCsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        this.csvReader = csvReader;
        this.csvRecordMapper = csvRecordMapper;
    }

    public void generateJSONFiles(String src) {
        generateJSONFiles(src, src);
    }

    public void generateJSONFiles(String src, String target) {
        new Processor(src, target).process();
    }

    private class Processor {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private final String src;
        private final String target;

        private List<Path> sourceFiles;
        private List<CsvRecord> csvRecords;
        private List<String> jsonStrings;

        public Processor(String src, String target) {
            this.src = src;
            this.target = target;
        }

        private void process() {
            createOutputDir();
            readFiles();
            mapToCsvRecord();
            generateJSONStrings();
            writeToFile();
        }

        private void writeToFile() {
            if (CollectionUtils.isNotEmpty(jsonStrings)) {
                IntStream.range(0, jsonStrings.size())
                        .forEach(index -> {
                            try {
                                Files.write(Paths.get(target, String.format("%d.json", index)),
                                        jsonStrings.get(index).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }

        private void generateJSONStrings() {
            jsonStrings = CollectionUtils.emptyIfNull(csvRecords)
                    .parallelStream()
                    .map(csvRecord -> {
                        try {
                            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(csvRecord);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        private void mapToCsvRecord() {
            csvRecords = csvRecordMapper.generateCsvRecords(sourceFiles);
        }

        private void readFiles() {
            sourceFiles = csvReader.getFiles(src);
        }

        private void createOutputDir() {
            try {
                Files.createDirectories(Paths.get(target).toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
