package com.viesca.csvprocessor.service.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.viesca.csvprocessor.dto.CsvRecord
import com.viesca.csvprocessor.service.filereader.CsvReader
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper
import org.apache.commons.collections4.CollectionUtils
import spock.lang.Shared
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
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
        CollectionUtils.isNotEmpty(csvRecords)
        System.out.println(csvRecords)
        csvRecords.size() == 4
    }

}
