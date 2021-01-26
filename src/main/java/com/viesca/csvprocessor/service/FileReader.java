package com.viesca.csvprocessor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FileReader {
    private final String extension;

    protected FileReader(String extension) {
        this.extension = extension;
    }


    public List<Path> getFiles(String directory) {
        try (Stream<Path> pathStream = Files.list(Paths.get(directory))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .filter(this::isHandled)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected boolean isHandled(Path path) {
        return path.getFileName().toString().endsWith(extension);
    }
}
