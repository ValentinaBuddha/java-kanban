package com.yandex.app.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String url = "http://localhost:8078";
    private String apiToken;
    HttpClient client = HttpClient.newHttpClient();

    public KVTaskClient() {
        register(url);
    }

    public void put(String key, String json) {
        if (apiToken == null) {
            System.out.println("API_TOKEN не присвоен");
            return;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/save" + key + "?API_TOKEN=" + apiToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    public String load(String key) {
        if (apiToken == null) {
            return "API_TOKEN не присвоен";
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/load" + key + "?API_TOKEN=" + apiToken))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return "Ошибка получения запроса";
    }

    private void register(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "/register"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                apiToken = response.body();
            } else {
                throw new RuntimeException("Не удалось получить API_TOKEN. Код ошибки: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса /register возникла ошибка.\n" +
                    "Проверьте, адрес и повторите попытку.");
        }
    }
}
