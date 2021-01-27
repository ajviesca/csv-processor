package com.viesca.csvprocessor.service.mapper


import com.viesca.csvprocessor.service.filereader.CsvReader
import org.apache.commons.collections4.CollectionUtils
import spock.lang.Shared
import spock.lang.Specification

class CsvRowMapperSpec extends Specification {

    @Shared
    def csvReader;

    @Shared
    def sut;

    def setupSpec() {
        csvReader = new CsvReader()
        sut = new CsvRecordMapper()
    }

    def "should map csv record"() {
        given: "csv files"
        def csvFiles = csvReader.getFiles("src/test/resources")
        when: "process is invoked"
        def csvRecords = sut.generateCsvRecords(csvFiles);
        then: 'csv records are created'
        CollectionUtils.isNotEmpty(csvRecords)
        System.out.println(csvRecords)
        csvRecords.size() == 4
    }

}
