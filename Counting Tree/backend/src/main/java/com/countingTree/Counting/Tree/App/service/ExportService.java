package com.countingTree.Counting.Tree.App.service;

import com.countingTree.Counting.Tree.App.model.Export;

public interface ExportService {

    Export exportDataToEXCEL();

    Export exportDataToPDF();

    Export exportDataToCSV();

}
