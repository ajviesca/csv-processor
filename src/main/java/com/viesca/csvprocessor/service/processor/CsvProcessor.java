package com.viesca.csvprocessor.service.processor;

import com.viesca.csvprocessor.dto.CsvRow;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class CsvProcessor {

    private final CsvReader csvReader;
    private final CsvRecordMapper csvRecordMapper;

    protected CsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        this.csvReader = csvReader;
        this.csvRecordMapper = csvRecordMapper;
    }

    public abstract void process(String src, String target);

    protected abstract class Processor {
        private final String srcDir;
        private final String targetDir;

        private List<Path> sourceFiles;
        private List<CsvRow> csvRows;

        protected Processor(String srcDir, String targetDir) {
            this.srcDir = srcDir;
            this.targetDir = targetDir;
        }

        protected void process() {
            createOutputDir();
            readFiles();
            mapToCsvRecord();
            formatOutput();
            writeToFile();
        }

        protected abstract void formatOutput();

        protected abstract String getFileType();

        protected void writeToFile() {
            CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .forEach(csvRow -> {
                        try {
                            Files.write(Paths.get(getTargetDir(),
                                    String.format(
                                            "%s-%d.%s",
                                            csvRow.getSourceFile(),
                                            csvRow.getIndex(),
                                            getFileType()
                                    )),
                                    csvRow.getFormattedOutput().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        private void mapToCsvRecord() {
            csvRows = csvRecordMapper.generateCsvRows(sourceFiles);
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

        public List<CsvRow> getCsvRecords() {
            return csvRows;
        }

        public String getTargetDir() {
            return targetDir;
        }
    }
}
