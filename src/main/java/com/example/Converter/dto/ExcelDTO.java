package com.example.Converter.dto;

import java.util.List;

public class ExcelDTO {
    private List<SheetDTO> sheets;

    public ExcelDTO(List<SheetDTO> sheets) {
        this.sheets = sheets;
    }

    // Getters e setters
    public List<SheetDTO> getSheets() { return sheets; }
    public void setSheets(List<SheetDTO> sheets) { this.sheets = sheets; }
}