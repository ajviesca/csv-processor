package com.viesca.csvprocessor.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.viesca.csvprocessor.constants.CsvFieldType;
import com.viesca.csvprocessor.dto.CsvField;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class JSONWriter {

    public String write(List<CsvField> csvFields) {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter writer = new StringWriter();

        try (JsonGenerator generator = jsonFactory.createGenerator(writer)) {
            generator.useDefaultPrettyPrinter();
            generator.writeStartObject();
            csvFields.forEach(csvField -> {
                try {
                    generator.writeFieldName(StringUtils.trim(csvField.getFieldName()));
                    generator.writeStartObject();
                    generator.writeFieldName("value");

                    if (CsvFieldType.NUMBER.equals(CsvFieldType.fromString(csvField.getType()))) {
                        generator.writeNumber(csvField.getValue());
                    } else {
                        generator.writeString(csvField.getValue());
                    }

                    generator.writeFieldName("type");
                    generator.writeString(csvField.getType());
                    generator.writeEndObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            generator.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

}
