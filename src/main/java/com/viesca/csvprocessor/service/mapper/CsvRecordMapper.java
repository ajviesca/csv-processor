package com.viesca.csvprocessor.service.mapper;

import com.viesca.csvprocessor.dto.CsvRow;
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

    private final CsvFieldMapper csvFieldMapper = new CsvFieldMapper();

    public List<CsvRow> generateCsvRecords(List<Path> csvFiles) {
        return CollectionUtils.emptyIfNull(csvFiles)
                .stream()
                .map(this::processCsvFile)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<CsvRow> processCsvFile(Path csvFile) {
        try {
            List<List<String>> rows = Files.readAllLines(csvFile).stream()
                    .map(line -> Arrays.asList(line.split(",")))
                    .collect(Collectors.toList());
            List<String> headerRows = rows.get(0);
            return IntStream.range(1, rows.size())
                    .mapToObj(index -> {
                        List<String> dataRows = rows.get(index);
                        return new CsvRow(
                                csvFile.getFileName().toString(),
                                index,
                                csvFieldMapper.generateCsvFields(headerRows, dataRows)
                        );
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
