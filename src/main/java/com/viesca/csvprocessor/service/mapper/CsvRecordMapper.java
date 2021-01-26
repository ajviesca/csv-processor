package com.viesca.csvprocessor.service.mapper;

import com.viesca.csvprocessor.dto.CsvRecord;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvRecordMapper {

    public List<CsvRecord> generateCsvRecords(List<Path> csvFiles) {
        return CollectionUtils.emptyIfNull(csvFiles)
                .stream()
                .map(this::processCsvFile)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<CsvRecord> processCsvFile(Path csvFile) {
        try {
            List<List<String>> rows = Files.readAllLines(csvFile).stream()
                    .map(line -> Arrays.asList(line.split(",")))
                    .collect(Collectors.toList());
            return IntStream.range(1, rows.size())
                    .mapToObj(index -> new CsvRecord(csvFile.getFileName().toString(), index, rows.get(0), rows.get(index)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
