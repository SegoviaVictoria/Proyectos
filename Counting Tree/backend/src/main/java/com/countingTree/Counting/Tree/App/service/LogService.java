package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Log;

public interface LogService {

    Log getLogById(Long logId);

    void addLog(Log log);

    void updateLog(Long logId, Log log);

    void deleteLog(Long logId);

    List<Log> getAllLogsByUserId(Long userId);
}
