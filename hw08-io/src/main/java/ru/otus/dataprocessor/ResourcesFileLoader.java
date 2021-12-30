package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {

        List<Measurement> measurement;
        String json;
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            json = new String(resourceStream.readAllBytes(), StandardCharsets.UTF_8);
            measurement = readDataFromJsonString(json);
        } catch (IOException exception) {
            throw new FileProcessException(exception);
        }

        //читает файл, парсит и возвращает результат
        return measurement;
    }

    List<Measurement> readDataFromJsonString(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("MeasurmentDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Measurement.class, new StdDeserializer<Measurement>((JavaType) null) {
            @Override
            public Measurement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                ObjectCodec codec = p.getCodec();
                JsonNode node = codec.readTree(p);
                JsonNode nameNode = node.get("name");
                JsonNode valueNode = node.get("value");
                String name = nameNode.asText();
                double value = valueNode.asDouble();
                return new Measurement(name, value);
            }
        });
        mapper.registerModule(module);
        return mapper.readValue(json, new TypeReference<List<Measurement>>() {
        });
    }

}
