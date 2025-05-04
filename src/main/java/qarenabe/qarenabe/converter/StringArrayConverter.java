package qarenabe.qarenabe.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StringArrayConverter implements AttributeConverter<String[], String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        if (attribute == null || attribute.length == 0) {
            return "";
        }
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new String[0];
        }
        return dbData.split(SPLIT_CHAR);
    }
}