package com.pmis.controllers;

import com.pmis.models.model.data_exploitation.CotDuLieu;
import com.pmis.payload.request.BarChartSeriesRequest;
import com.pmis.payload.request.ChartRequest;
import com.pmis.payload.response.service.ExecServiceResponse;
import com.pmis.services.service.ChartService;
import com.pmis.services.service.DataExploitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chart")
@PreAuthorize("isAuthenticated()")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private DataExploitationService dataExploitationService;

    @PostMapping
    public ResponseEntity<ExecServiceResponse> getChartSeries(@RequestBody ChartRequest chartRequest) {
        if (chartRequest.getCategories().length == 0 || chartRequest.getLines().length == 0) {
            return ResponseEntity.ok(new ExecServiceResponse(List.of(), 1, ""));
        }
        List<Map<String, Object>> data = dataExploitationService.getDataExploitation(chartRequest.getDataExploitation(), false, false);
        List<CotDuLieu> rows = dataExploitationService.getCotDuLieus();

        return ResponseEntity.ok(new ExecServiceResponse(chartService.getChartSeries(data, rows, chartRequest), 1, ""));
    }

    @PostMapping("/bar")
    public ResponseEntity<ExecServiceResponse> getBarChartSeries(@RequestBody BarChartSeriesRequest request) {
        if (request.getColumns().length == 0) {
            return ResponseEntity.ok(new ExecServiceResponse(List.of(), 1, ""));
        }
        List<Map<String, Object>> data = dataExploitationService.getDataExploitation(request.getDataExploitation(), false, false);
        List<CotDuLieu> rows = dataExploitationService.getCotDuLieus();
        return ResponseEntity.ok(new ExecServiceResponse(chartService.getBarChartSeries(data, rows, request), 1, ""));
    }
}
