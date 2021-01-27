package com.viesca.csvprocessor.service.mapper;

import com.viesca.csvprocessor.constants.CsvFieldType;
import com.viesca.csvprocessor.dto.CsvField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvFieldMapper {

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    public List<CsvField> generateCsvFields(List<String> headerRows, List<String> dataRows) {
        if (CollectionUtils.size(headerRows) != CollectionUtils.size(dataRows)) {
            throw new IllegalArgumentException("mismatch header and values length");
        }

        return IntStream.range(0, headerRows.size())
                .mapToObj(i -> new CsvField(headerRows.get(i), getType(dataRows.get(i)), dataRows.get(i)))
                .collect(Collectors.toList());
    }

    private String getType(String s) {
        if (NumberUtils.isCreatable(s)) {
            return CsvFieldType.NUMBER.getDisplayName();
        } else if (emailValidator.isValid(s)) {
            return CsvFieldType.EMAIL.getDisplayName();
        } else if (isValidNumber(s)) {
            return CsvFieldType.PHONE_NUMBER.getDisplayName();
        }

        return CsvFieldType.STRING.getDisplayName();
    }

    private boolean isValidNumber(String possibleNumber) {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
        Matcher matcher = pattern.matcher(possibleNumber);
        return matcher.matches();
    }
}
