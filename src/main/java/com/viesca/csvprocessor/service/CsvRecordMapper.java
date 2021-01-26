package com.viesca.csvprocessor.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.viesca.csvprocessor.dto.CsvRecord;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvRecordMapper {

    private final ObjectReader objectReader;

    public CsvRecordMapper() {
        objectReader = new CsvMapper()
                .readerFor(CsvRecord.class)
                .with(CsvSchema
                        .emptySchema()
                        .withHeader()
                );
    }

    public List<CsvRecord> generateCsvRecords(List<Path> csvFiles) {
        return CollectionUtils.emptyIfNull(csvFiles)
                .stream()
                .map(this::processCsvFile)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(csvRecords -> csvRecords.stream())
                .collect(Collectors.toList());
    }

    private List<CsvRecord> processCsvFile(Path csvFile) {

        try {
            MappingIterator<CsvRecord> objectMappingIterator = objectReader.readValues(csvFile.toFile());
            return objectMappingIterator.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
