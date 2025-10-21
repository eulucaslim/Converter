package com.example.Converter.services;

import com.example.Converter.dto.ExcelDTO;
import com.example.Converter.dto.SheetDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class TempFileService {
    private String tempPath;

    public boolean handleTempFileUpload(MultipartFile file) throws IOException {
        try {
            String tempDirPath = System.getProperty("user.dir") + "/temp";
            File tempDir = new File(tempDirPath);
            // Case not exists temp dir
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            // Generate a temp file
            File tempFile = new File(tempDir, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(tempFile);
            tempPath = tempFile.getAbsolutePath();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public ExcelDTO processExcel() throws IOException {
        File tempFile = new File(tempPath);
        List<SheetDTO> sheetList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(tempFile)) {

            for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheetAt(s);

                // Map the content
                Map<String, Map<String, String>> content = new LinkedHashMap<>();
                Map<String, String> mergedCellsMap = new HashMap<>();

                for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                    CellRangeAddress range = sheet.getMergedRegion(i);
                    Cell firstCell = sheet.getRow(range.getFirstRow()).getCell(range.getFirstColumn());
                    String value = getCellValue(firstCell);

                    for (int r = range.getFirstRow(); r <= range.getLastRow(); r++) {
                        for (int c = range.getFirstColumn(); c <= range.getLastColumn(); c++) {
                            mergedCellsMap.put(r + "," + c, value);
                        }
                    }
                }

                for (Row row : sheet) {
                    int rowNum = row.getRowNum();
                    for (Cell cell : row) {
                        int colNum = cell.getColumnIndex();
                        String key = rowNum + "," + colNum;
                        String cellValue = mergedCellsMap.getOrDefault(key, getCellValue(cell));
                        String colKey = String.valueOf(colNum);
                        String rowKey = String.valueOf(rowNum);

                        content.computeIfAbsent(colKey, k -> new LinkedHashMap<>()).put(rowKey, cellValue);
                    }
                }

                sheetList.add(new SheetDTO(sheet.getSheetName(), content));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Remove Temp File
        boolean deleted = tempFile.delete();

        if (deleted) {
            System.out.println("Temp File Removed!");
        } else {
            System.out.println("Error to Temp File Removed!");
        }
        return new ExcelDTO(sheetList);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> String.valueOf(cell.getCellFormula());
            default -> "";
        };
    }
}
