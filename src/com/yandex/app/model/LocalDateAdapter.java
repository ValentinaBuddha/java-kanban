package com.yandex.app.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    public class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

        public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDatetime) throws IOException {
            if (localDatetime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDatetime.format(formatter));
            }
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            String nullOrNot = jsonReader.nextString();
            if (nullOrNot.equals("null")) {
                return null;
            }
            return LocalDateTime.parse(nullOrNot, formatter);
        }
    }
