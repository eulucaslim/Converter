package com.example.Converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SheetDTO {

    @JsonProperty("sheet_name")
    private String sheetName;

    @JsonProperty("content")
    private List<List<String>> content;

    public SheetDTO(String sheetName, List<List<String>> content) {
        this.sheetName = sheetName;
        this.content = content;
    }

    // Getters e setters
    public String getSheetName() { return sheetName; }
    public void setSheetName(String sheetName) { this.sheetName = sheetName; }

    public List<List<String>> getContent() { return content; }
    public void setContent(List<List<String>> content) { this.content = content; }
}