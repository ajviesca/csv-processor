package com.viesca.csvprocessor.service.processor

import com.viesca.csvprocessor.service.filereader.CsvReader
import com.viesca.csvprocessor.service.filereader.JSONReader
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper
import com.viesca.csvprocessor.service.processor.JSONOutputCsvProcessor
import org.apache.commons.io.FileUtils
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Paths

class JSONOutputCsvProcessorSpec extends Specification {
    def OUTPUT_DIR = 'src/test/resources/output'

    @Shared
    def sut;

    def setupSpec() {
        sut = new JSONOutputCsvProcessor(new CsvReader(), new CsvRecordMapper());
    }

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

    def "shoud generate json files"() {
        given: "source directory"
        def src = 'src/test/resources'
        when: "generate json is invoked"
        sut.generateJSONFiles(src, OUTPUT_DIR);
        then: "should generate json files"
        new JSONReader().getFiles(OUTPUT_DIR).size() == 5
    }

}
