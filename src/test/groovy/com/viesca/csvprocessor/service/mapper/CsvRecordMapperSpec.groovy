package com.viesca.csvprocessor.service.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.viesca.csvprocessor.dto.CsvRecord
import com.viesca.csvprocessor.service.filereader.CsvReader
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Paths

class CsvRecordMapperSpec extends Specification {

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
        for (def csvRecord : csvRecords) {
            assert getExpectedJsonData().equals(csvRecord)
        }
    }

    private getExpectedJsonData() {
        new ObjectMapper().readValue(Paths.get('src/test/resources/expected.json').toFile(), CsvRecord.class);
    }

}
