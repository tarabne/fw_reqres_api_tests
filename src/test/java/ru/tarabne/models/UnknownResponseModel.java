package ru.tarabne.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UnknownResponseModel {
    int page,
        total;
    @JsonProperty("total_pages")
    int totalPages;
    @JsonProperty("per_page")
    int perPage;
    List<ColorData> data;
    Support support;

    @Data
    public static class ColorData {
        int id,
            year;
        String name,
            color;
        @JsonProperty("pantone_value")
        String pantoneValue;
    }

    @Data
    public static class Support {
        String url,
            text;
    }
}
