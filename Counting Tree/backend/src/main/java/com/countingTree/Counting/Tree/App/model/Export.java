package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "exports")
public class Export {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exportId;

    @Column(name = "export_date", nullable = false)
    private LocalDateTime exportDate;

    @Column(name = "file_path", nullable = false)
    private String filePath;


    // -------------------------------------------------------- RELATIONS

    @Enumerated(EnumType.STRING)
    private ExportFormat format;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User generatedBy;

    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

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