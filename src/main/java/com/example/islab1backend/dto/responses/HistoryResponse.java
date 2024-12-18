package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.ImportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {
    private Long id;
    private String fileName;
    private String username;
    private String importTime;
    private int numberOfImportedRecords;
    private ImportStatus status;
}
