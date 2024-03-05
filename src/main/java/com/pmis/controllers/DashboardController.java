package com.pmis.controllers;

import com.pmis.annotation.CurrentUser;
import com.pmis.models.entity.Dashboard;
import com.pmis.models.model.data_exploitation.CotDuLieu;
import com.pmis.models.model.data_exploitation.NhomDuLieu;
import com.pmis.payload.dto.CotDuLieuDTO;
import com.pmis.payload.response.DashboardChartOptionsResponse;
import com.pmis.payload.response.DashboardResponse;
import com.pmis.payload.response.service.ExecServiceResponse;
import com.pmis.repository.DashboardRepository;
import com.pmis.security.services.UserDetailsImpl;
import com.pmis.services.service.DashboardService;
import com.pmis.services.service.DataExploitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("isAuthenticated()")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private DataExploitationService dataExploitationService;

    @GetMapping("/data/{dashboardId}")
//    public ResponseEntity<ExecServiceResponse> findAllByUser(@CurrentUser UserDetailsImpl userDetails) {
//        Dashboard dashboard = dashboardService.findByUserId(userDetails.getUserId());
//        if (dashboard == null) {
//            return ResponseEntity.ok(new ExecServiceResponse(List.of(), 2, "Không lấy được dữ liệu dashboard"));
//        }
//        String[] lstCharts = dashboard.getCharts().split(";");
//        List<String> lstMaDuLieu;
//        Set<String> maDuLieuDistinct;
//
//        try {
//            lstMaDuLieu = dashboardService.findAllMaDuLieuOfDashboard(Arrays.asList(lstCharts));
//            maDuLieuDistinct = new HashSet<>(lstMaDuLieu);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return ResponseEntity.ok(new ExecServiceResponse(new ArrayList<>(), 0, "Xảy ra lỗi khi lấy danh sách biểu đồ"));
//        }
//
//        List<NhomDuLieu> nhomDuLieus = new ArrayList<>();
//        maDuLieuDistinct.forEach(e -> {
//            NhomDuLieu response = new NhomDuLieu();
//            response.setMaNhomDuLieu(e);
//            response.setData(dataExploitationService.getDataExploitation(e, false, false));
//            response.setRows(dataExploitationService.getCotDuLieus());
//            nhomDuLieus.add(response);
//        });
//
//        try {
//            List<DashboardChartOptionsResponse> chartOptions = dashboardService.getAllChartOptionsOfDashboard(
//                    List.of(lstCharts), List.of(dashboard.getPosition().split(";")), lstMaDuLieu, nhomDuLieus
//            );
//            return ResponseEntity.ok(new ExecServiceResponse(new DashboardResponse(dashboard.getId(), dashboard.getLayout(), chartOptions), 1, ""));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.ok(new ExecServiceResponse(new ArrayList<>(), 0, "Xảy ra lỗi khi lấy danh sách biểu đồ"));
//        }
//    }
    public ResponseEntity<ExecServiceResponse> findAllByDashboard(@PathVariable String dashboardId) {
        Dashboard dashboard = dashboardService.findByDashboardId(dashboardId);
        if (dashboard == null) {
            return ResponseEntity.ok(new ExecServiceResponse(List.of(), 2, "Không lấy được dữ liệu dashboard"));
        }
        String[] lstCharts = dashboard.getCharts().split(";");
        List<String> lstMaDuLieu;
        Set<String> maDuLieuDistinct;

        try {
            lstMaDuLieu = dashboardService.findAllMaDuLieuOfDashboard(Arrays.asList(lstCharts));
            maDuLieuDistinct = new HashSet<>(lstMaDuLieu);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ExecServiceResponse(new ArrayList<>(), 0, "Xảy ra lỗi khi lấy danh sách biểu đồ"));
        }

        List<NhomDuLieu> nhomDuLieus = new ArrayList<>();
        maDuLieuDistinct.forEach(e -> {
            NhomDuLieu response = new NhomDuLieu();
            response.setMaNhomDuLieu(e);
            response.setData(dataExploitationService.getDataExploitation(e, false, false));
            response.setRows(dataExploitationService.getCotDuLieus());
            nhomDuLieus.add(response);
        });

        try {
            List<DashboardChartOptionsResponse> chartOptions = dashboardService.getAllChartOptionsOfDashboard(
                    List.of(lstCharts), List.of(dashboard.getPosition().split(";")), lstMaDuLieu, nhomDuLieus
            );
            return ResponseEntity.ok(new ExecServiceResponse(new DashboardResponse(dashboard.getId(), dashboard.getLayout(), dashboard.getName(),  chartOptions), 1, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ExecServiceResponse(new ArrayList<>(), 0, "Xảy ra lỗi khi lấy danh sách biểu đồ"));
        }
    }
//@PostMapping("/addDashboard")
//    public ResponseEntity<ExecServiceResponse> addDashboard(@RequestBody Dashboard dashboard) {
//        dashboardRepository.save(dashboard);
//        return ResponseEntity.ok(new DashboardResponse(dashboard.getId(), dashboard.getLayout(), dashboard.getCharts()));
//
//    }
}
