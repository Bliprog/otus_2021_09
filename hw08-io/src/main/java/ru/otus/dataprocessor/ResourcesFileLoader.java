package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        URL resourceURL = getClass().getClassLoader().getResource(fileName);
        List<Measurement> measurement = null;
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(URI.create(resourceURL.toString()))));
            measurement = readDataFromJsonString(json);
        } catch (IOException exception) {
            System.out.println("Ошибка при чтении файла");
            return null;
        }

        //читает файл, парсит и возвращает результат
        return measurement;
    }

    List<Measurement> readDataFromJsonString(String json) {
        return new Gson().fromJson(json, new TypeReference<List<Measurement>>() {
        }.getType());
    }

}
