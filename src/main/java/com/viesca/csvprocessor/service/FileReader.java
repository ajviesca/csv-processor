package com.viesca.csvprocessor.service;

import java.nio.file.Path;

public abstract class FileReader {
    private final String extension;

    public FileReader(String extension) {
        this.extension = extension;
    }

    protected boolean isHandled(Path path) {
        return path.getFileName().toString().endsWith(extension);
    }
}
