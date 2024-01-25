package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ReportDao;
import com.teamc.moodtracker.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    @Transactional
    public void addReport(ReportDto dto) {
        reportDao.addReport(dto);
    }

}
