package com.viesca.csvprocessor.service

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class FileReaderSpec extends Specification {

    @Unroll
    def "#reader :: should read #fileType files from a given directory"() {
        given: "a given directory"
        def directory = 'src/test/resources'
        when:
        def files = reader.getFiles(directory)
        then:
        files.size() == resultSize
        where:
        reader            | fileType || resultSize
        new CsvReader()   | 'csv'    || 2
        new JSONReader() || 'json'   || 1
    }
}
