package com.viesca.csvprocessor.service.processor;

import com.viesca.csvprocessor.dto.CsvRecord;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLOutputCsvProcessor extends CsvProcessor {

    public XMLOutputCsvProcessor(CsvReader csvReader, CsvRecordMapper csvRecordMapper) {
        super(csvReader, csvRecordMapper);
    }

    @Override
    public void process(String src, String target) {
        new XMLProcessor(src, target).process();
    }

    private class XMLProcessor extends Processor {

        protected XMLProcessor(String srcDir, String targetDir) {
            super(srcDir, targetDir);
        }

        @Override
        protected void formatOutput() {
            CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .forEach(csvRecord -> {
                        try {
                            JAXBContext jaxbContext = JAXBContext.newInstance(CsvRecord.class);
                            Marshaller marshaller = jaxbContext.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                            StringWriter writer = new StringWriter();
                            marshaller.marshal(csvRecord, writer);
                            csvRecord.setFormattedOutput(writer.toString());
                        } catch (JAXBException e) {
                            e.printStackTrace();
                        }
                    });
        }

        @Override
        protected String getFileType() {
            return "xml";
        }

    }
}
