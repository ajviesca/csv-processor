package com.viesca.csvprocessor.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class JSONWriter {

    public String write(Map<String, String> headerValuePair) {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter writer = new StringWriter();

        try (JsonGenerator generator = jsonFactory.createGenerator(writer)) {
            generator.useDefaultPrettyPrinter();
            generator.writeStartObject();

            for (Map.Entry<String, String> entry : headerValuePair.entrySet()) {
                generator.writeFieldName(StringUtils.trim(entry.getKey()));
                generator.writeString(StringUtils.trim(entry.getValue()));
            }

            generator.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

}
