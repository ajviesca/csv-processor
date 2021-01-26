package com.viesca.csvprocessor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader extends FileReader {

    private static final String EXTENSION = "csv";

    public CsvReader() {
        super(EXTENSION);
    }

    public List<Path> getFiles(String directory) throws IOException {
        try (Stream<Path> pathStream = Files.list(Paths.get(directory))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .filter(this::isCsv)
                    .collect(Collectors.toList());
        }
    }

    private boolean isCsv(Path path) {
        return super.isHandled(path);
    }
}
