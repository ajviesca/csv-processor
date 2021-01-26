package com.viesca.csvprocessor.service

import spock.lang.Shared
import spock.lang.Specification

class CsvReaderSpec extends Specification {

    @Shared
    def sut;

    def setupSpec() {
        sut = new CsvReader();
    }

    def "should read csv files from a given directory"() {
        given: "a given directory"
        def directory = 'src/test/resources'
        when:
        def files = sut.getFiles(directory)
        then:
        files.size == 2
    }
}
