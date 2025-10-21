package com.example.Converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class SheetDTO {

    @JsonProperty("sheet_name")
    private String sheetName;

    @JsonProperty("content")
    private Map <String, Map<String, String>> content;

    public SheetDTO(String sheetName, Map<String, Map<String, String>> content) {
        this.sheetName = sheetName;
        this.content = content;
    }

    // Getters e setters
    public String getSheetName() { return sheetName; }
    public void setSheetName(String sheetName) { this.sheetName = sheetName; }

    public Map<String, Map<String, String>> getContent() { return content; }
    public void setContent(Map<String, Map<String, String>> content) { this.content = content; }
}