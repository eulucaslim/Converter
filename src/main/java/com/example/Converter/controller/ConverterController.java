package com.example.Converter.controller;

import com.example.Converter.dto.ExcelDTO;
import com.example.Converter.services.TempFileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ConverterController {

    @RequestMapping(path = "/export-json", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExcelDTO> exportJson(@RequestPart("file") MultipartFile file) {
        try {
            TempFileService tempFileService = new TempFileService();

            if (!tempFileService.handleTempFileUpload(file)) {
                return ResponseEntity.badRequest().build();
            }

            ExcelDTO data = tempFileService.processExcel();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}