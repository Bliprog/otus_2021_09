package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
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
        try(InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            json = new String(resourceStream.readAllBytes(), StandardCharsets.UTF_8);
            measurement = readDataFromJsonString(json);
        } catch (IOException exception) {
          throw new FileProcessException(exception);
        }

        //читает файл, парсит и возвращает результат
        return measurement;
    }

    List<Measurement> readDataFromJsonString(String json) throws IOException {
        //Этот вариант не работает
        //ObjectMapper mapper = new ObjectMapper();
        //return  mapper.readValue(json, new TypeReference<List<Measurement>>(){});
        return new Gson().fromJson(json, new TypeReference<List<Measurement>>() {}.getType());
    }

}
