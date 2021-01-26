package com.viesca.csvprocessor.service.processor

import com.viesca.csvprocessor.service.filereader.CsvReader
import com.viesca.csvprocessor.service.filereader.JSONReader
import com.viesca.csvprocessor.service.filereader.XMLReader
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper
import org.apache.commons.io.FileUtils
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Paths

class CsvProcessorSpec extends Specification {
    def OUTPUT_DIR = 'src/test/resources/output'

    def setup() {
        try {
            FileUtils.cleanDirectory(Paths.get(OUTPUT_DIR).toFile())
        } catch (Exception e) {
            //swallow
        }
    }

    def cleanup() {
        try {
            FileUtils.cleanDirectory(Paths.get(OUTPUT_DIR).toFile())
        } catch (Exception e) {
            //swallow
        }
    }

    @Unroll
    def "shoud generate #fileType files"() {
        given: "source directory"
        def src = 'src/test/resources'
        when: "generate json is invoked"
        sut.process(src, OUTPUT_DIR);
        then: "should generate json files"
        reader.getFiles(OUTPUT_DIR).size() == expectedResult
        where:
        fileType | sut                                                                | reader           || expectedResult
        'json'   | new JSONOutputCsvProcessor(new CsvReader(), new CsvRecordMapper()) | new JSONReader() || 5
        'xml'    | new XMLOutputCsvProcessor(new CsvReader(), new CsvRecordMapper())  | new XMLReader()  || 5
    }

}
