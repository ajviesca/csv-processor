package com.viesca.csvprocessor.service.processor;

import com.viesca.csvprocessor.dto.CsvRecord;
import com.viesca.csvprocessor.service.filereader.CsvReader;
import com.viesca.csvprocessor.service.mapper.CsvRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Objects;
import java.util.stream.Collectors;

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
        protected void generateStringData() {
            setDataStrings(CollectionUtils.emptyIfNull(getCsvRecords())
                    .parallelStream()
                    .map(csvRecord -> {
                        try {
                            JAXBContext jaxbContext = JAXBContext.newInstance(CsvRecord.class);
                            Marshaller marshaller = jaxbContext.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                            StringWriter writer = new StringWriter();
                            marshaller.marshal(csvRecord, writer);
                            return writer.toString();
                        } catch (JAXBException e) {
                            e.printStackTrace();
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }

        @Override
        protected String getFileType() {
            return "xml";
        }

    }
}
