package ru.tarabne.models.lombok;

import lombok.Data;

import java.util.List;

@Data
public class UnknownResponseModel {
    int page, per_page, total, total_pages;
    List<ColorData> data;
    Support support;

    @Data
    public static class ColorData {
        int id, year;
        String name, color, pantone_value;
    }

    @Data
    public static class Support {
        String url, text;
    }
}
