package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;

public class Export {

    private Long exportId;
    private ExportFormat format;
    private LocalDateTime exportDate;
    private String filePath;

    private User generatedBy;

    public Export(Long exportId, ExportFormat format, LocalDateTime exportDate, String filePath, User generatedBy) {
        this.exportId = exportId;
        this.format = format;
        this.exportDate = exportDate;
        this.filePath = filePath;
        this.generatedBy = generatedBy;
    }

    public Export() {
    }

    public Long getExportId() {
        return exportId;
    }

    public void setExportId(Long exportId) {
        this.exportId = exportId;
    }

    public ExportFormat getFormat() {
        return format;
    }

    public void setFormat(ExportFormat format) {
        this.format = format;
    }

    public LocalDateTime getExportDate() {
        return exportDate;
    }

    public void setExportDate(LocalDateTime exportDate) {
        this.exportDate = exportDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

}