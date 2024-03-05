package com.pmis.services.service;

import com.pmis.constant.Align;
import com.pmis.constant.Chieu;
import com.pmis.constant.DataType;
import com.pmis.constant.Position;
import com.pmis.constant.enumerate.EChartType;
import com.pmis.models.entity.Chart;
import com.pmis.models.entity.Dashboard;
import com.pmis.models.model.chart.ChartOptions;
import com.pmis.models.model.chart.chart.Animation;
import com.pmis.models.model.chart.dataLabels.DataLabels;
import com.pmis.models.model.chart.legend.Legend;
import com.pmis.models.model.chart.markers.Marker;
import com.pmis.models.model.chart.plotOptions.Bar;
import com.pmis.models.model.chart.plotOptions.PlotOptions;
import com.pmis.models.model.chart.style.Style;
import com.pmis.models.model.chart.title.Title;
import com.pmis.models.model.chart.xaxis.XAxis;
import com.pmis.models.model.chart.yaxis.YAxis;
import com.pmis.models.model.data_exploitation.CotDuLieu;
import com.pmis.models.model.data_exploitation.NhomDuLieu;
import com.pmis.payload.response.ChartResponse;
import com.pmis.payload.response.DashboardChartOptionsResponse;
import com.pmis.payload.response.EmptyJsonBody;
import com.pmis.repository.ChartRepository;
import com.pmis.repository.DashboardRepository;
import com.pmis.util.CustomEqualsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ChartRepository chartRepository;

    public Dashboard findByUserId(String userId) {
        List<Dashboard> dashboards = dashboardRepository.findByUserId(userId);
        return !dashboards.isEmpty() ? dashboards.get(0) : null;
    }
public Dashboard findByDashboardId(String id) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        return dashboard.orElse(null);
}
    public Dashboard findById(String dashboardId) {
        return dashboardRepository.findById(dashboardId).orElse(null);
    }

    public List<String> findAllMaDuLieuOfDashboard(List<String> lstCharts) throws SQLException {
        List<String> res = new ArrayList<>();
        lstCharts.forEach(e -> {
            String sql = "SELECT MA_DULIEU FROM BCTM_BIEUDO WHERE MA_BIEUDO = :chartId";
            List<String> resultSet = jdbcTemplate.query(sql, new MapSqlParameterSource("chartId", e), (rs, rowNum) -> rs.getString("MA_DULIEU"));
            if (!resultSet.isEmpty()) {
                res.add(resultSet.get(0));
            }
        });
        return res;
    }

    public String findMaDuLieuByChartId(String chartId) throws SQLException {
        String sql = "SELECT MA_DULIEU FROM BCTM_BIEUDO WHERE MA_BIEUDO = :chartId";
        List<String> resultSet = jdbcTemplate.query(sql, new MapSqlParameterSource("chartId", chartId), (rs, rowNum) -> rs.getString("MA_DULIEU"));
        if (!resultSet.isEmpty()) {
            return resultSet.get(0);
        }
        return null;
    }

    public List<DashboardChartOptionsResponse> getAllChartOptionsOfDashboard(
            List<String> chartIds,
            List<String> layouts,
            List<String> dataExploitations,
            List<NhomDuLieu> nhomDuLieus
    ) {
        List<DashboardChartOptionsResponse> responses = new ArrayList<>();
        int index = 0;
        for (String layout: layouts) {
            DashboardChartOptionsResponse response = new DashboardChartOptionsResponse();
            response.setLayout(layout);
            String maDuLieu = dataExploitations.get(index);
            NhomDuLieu nhomDuLieu = nhomDuLieus.stream().filter(e -> e.getMaNhomDuLieu().equals(maDuLieu)).toList().get(0);
            response.setChartOptions(getChartOptions(chartIds.get(index), nhomDuLieu.getData(), nhomDuLieu.getRows()));
            responses.add(response);
            index++;
        }
        return responses;
    }

    private ChartOptions getChartOptions (
            String chartId,
            List<Map<String, Object>> data,
            List<CotDuLieu> rows
    ) {
        Optional<Chart> chartOptional = chartRepository.findById(chartId);
        if (chartOptional.isEmpty() || data.isEmpty() || rows.isEmpty()) {
            return null;
        }
        Chart chart = chartOptional.get();
        if (chart.getChartType().equals(EChartType.PIE)) {
            String[] cols = chart.getTenCot().split(";");
            return getPieChartOptions(
                    chart.getMoTa(), cols[0], cols[1], data, rows
            );
        } else if (chart.getChartType().equals(EChartType.BAR)) {
            return getBarChartOptions(chart, data, rows);
        } else {
            return getLineChartOptions(chart, data, rows);
        }
    }

    private ChartOptions getPieChartOptions(
            String chartName,
            String groupCol,
            String dataCol,
            List<Map<String, Object>> data,
            List<CotDuLieu> rows
    ) {
        Legend legend = new Legend(true, Position.RIGHT, Align.CENTER);
        Title title = new Title(
                chartName,
                Align.CENTER,
                10,
                0,
                15,
                false,
                getTitleStyle(),
                Position.TOP
        );
        com.pmis.models.model.chart.chart.Chart chart =
                new com.pmis.models.model.chart.chart.Chart("100%", com.pmis.constant.Chart.PIE, new Animation());

        // get series data and labels
        Set<Object> labelSet = new TreeSet<>();
        List<Object> dataArray = new ArrayList<>();

        data.forEach(e -> labelSet.add(e.get(groupCol)));
        labelSet.forEach(e -> {
            AtomicReference<Double> sum = new AtomicReference<>(0.0);
            data.forEach(ee -> {
                if (ee.get(groupCol).equals(e) && ee.get(dataCol) != null) {
                    try {
                        sum.updateAndGet(v -> (v + (ee.get(dataCol) != null ? ((BigDecimal) ee.get(dataCol)).doubleValue() : 0.0)));
                    } catch (ClassCastException classCastException) {
                        sum.updateAndGet(v -> (v + (ee.get(dataCol) != null ? ((Integer) ee.get(dataCol)).doubleValue() : 0.0)));
                    }
                }
            });
            dataArray.add(sum.get());
        });

        return new ChartOptions(
                chart,
                dataArray,
                labelSet.stream().map(e -> this.mapLabelValue(e, groupCol, rows)),
                title,
                new EmptyJsonBody(),
                new EmptyJsonBody(),
                null,
                new EmptyJsonBody(),
                legend,
                new DataLabels(),
                new Marker()
        );
    }

    private ChartOptions getBarChartOptions(
            Chart chart,
            List<Map<String, Object>> data,
            List<CotDuLieu> rows
    ) {
        Title title = new Title(
                chart.getMoTa(),
                Align.CENTER,
                10,
                0,
                15,
                false,
                getTitleStyle(),
                Position.TOP
        );
        com.pmis.models.model.chart.chart.Chart chartOfChartOptions =
                new com.pmis.models.model.chart.chart.Chart("100%", com.pmis.constant.Chart.BAR, new Animation());
        DataLabels dataLabels = new DataLabels(false, null);
        PlotOptions plotOptions = new PlotOptions(new Bar(chart.getChieu().equals(Chieu.NGANG), new DataLabels(false, null)));
        List<ChartResponse> allSeries = new ArrayList<>();
        Object xAxis;

        String columnX;
        List<String> listColumnsY;
        String[] truc = chart.getTruc().split(";");
        String[] columnTemp = chart.getTenCot().split(";");

        Object yAxis;
        if (chart.getChieu().equals(Chieu.NGANG)) {
            yAxis = new YAxis(
                    new Title("", null, null, null, -10, null, getXAxisTitleStyle(), Position.BOTTOM)
            );
        } else {
            yAxis = new EmptyJsonBody();
        }

        if (truc[0].equals("x")) {
            columnX = columnTemp[0];
            listColumnsY = Arrays.stream(columnTemp).toList().subList(1, columnTemp.length);
        } else {
            columnX = "";
            listColumnsY = Arrays.stream(columnTemp).toList();
        }

        if (!columnX.isEmpty()) {
            Set<Object> categories = new TreeSet<>();
            for (Map<String, Object> e: data) {
                categories.add(e.get(columnX));
            }

            listColumnsY.forEach(e -> {
                ChartResponse chartResponse = new ChartResponse(e, new ArrayList<>());
                allSeries.add(chartResponse);
            });

            for (ChartResponse series: allSeries) {
                List<Object> seriesData = new ArrayList<>();
                for (Object e: categories) {
                    AtomicReference<Double> sum = new AtomicReference<>(0.0);
                    if (!data.isEmpty()) {
                        List<Map<String, Object>> vals = data.stream().filter(ee -> ee.get(columnX) != null && ee.get(columnX).equals(e)).toList();
//                        if (!vals.isEmpty() && vals.get(0).get(series.getName()) != null) {
//                            sum = ((BigDecimal) vals.get(0).get(series.getName())).doubleValue();
//                        }
                        vals.forEach(ee -> {
                            try {
                                sum.updateAndGet(v -> v + (ee.get(series.getName()) != null ? ((BigDecimal) ee.get(series.getName())).doubleValue() : 0.0));
                            } catch (ClassCastException classCastException) {
                                sum.updateAndGet(v -> v + (ee.get(series.getName()) != null ? ((Integer) ee.get(series.getName())).doubleValue() : 0.0));
                            }
                        });
                    }
                    if (sum.get() == 0.0) {
                        seriesData.add(null);
                    } else {
                        seriesData.add(sum);
                    }
                }
                series.setData(seriesData);
            }
            XAxis xaxis = new XAxis();
            xaxis.setCategories(categories.stream().map(e -> mapLabelValue(e, columnX, rows)));
            xaxis.setType("category");

            if (!chart.getChieu().equals(Chieu.NGANG)) {
                for (CotDuLieu e: rows) {
                    String text = "";
                    if (e.getTEN_COT().equals(columnX)) {
                        text = e.getMO_TA();
                    }
                    xaxis.setTitle(new Title(text, null, null, null, -10, null, getXAxisTitleStyle(), Position.BOTTOM));
                }
            } else {
                xaxis.setTitle(new EmptyJsonBody());
            }

            xAxis = xaxis;
        } else {
            for (String e: listColumnsY) {
                ChartResponse chartResponse = new ChartResponse(e, new ArrayList<>());
                chartResponse.getData().add(data.get(0).get(e));
                allSeries.add(chartResponse);
            }
            XAxis xaxis = new XAxis();
            xaxis.setCategories(List.of(" "));
            xaxis.setTitle(new EmptyJsonBody());
            xaxis.setType("");
            xAxis = xaxis;
        }

        allSeries.forEach(e -> {
            List<CotDuLieu> cotDuLieusTmp = rows.stream().filter(ee -> ee.getTEN_COT().equals(e.getName())).toList();
            if (!cotDuLieusTmp.isEmpty()) {
                e.setName(cotDuLieusTmp.get(0).getMO_TA());
            }
        });

        return new ChartOptions(
                chartOfChartOptions,
                allSeries,
                new EmptyJsonBody(),
                title,
                xAxis,
                yAxis,
                Arrays.stream(chart.getMauSac().split(";")).toList(),
                plotOptions,
                new EmptyJsonBody(),
                dataLabels,
                new Marker()
        );
    }

    private ChartOptions getLineChartOptions(
            Chart chartEntity,
            List<Map<String, Object>> data,
            List<CotDuLieu> rows
    ) {
        String[] lstCot = chartEntity.getTenCot().split(";");
        String xCoord = lstCot[0];
        List<String> lines = Arrays.stream(lstCot).toList().subList(1, lstCot.length);

        com.pmis.models.model.chart.chart.Chart chart =
                new com.pmis.models.model.chart.chart.Chart("100%", com.pmis.constant.Chart.LINE, new Animation());
        Title title = new Title(
                chartEntity.getMoTa(),
                Align.CENTER,
                10, 0, 15, false,
                getTitleStyle(),
                Position.TOP
        );

        XAxis xAxis = new XAxis();
        xAxis.setType("category");

        String maKieuDuLieu = "";
        for (CotDuLieu e: rows) {
            if (xCoord.equals(e.getTEN_COT())) {
                maKieuDuLieu = e.getMA_KIEU_DLIEU();
            }
        }

        Set<Object> categories = new TreeSet<>();
        data.forEach(e -> categories.add(e.get(xCoord)));

        List<ChartResponse> allSeries = new ArrayList<>();
        for (String line: lines) {
            ChartResponse chartResponse = new ChartResponse(line, new ArrayList<>());
            allSeries.add(chartResponse);
        }

        for (Map<String, Object> itr: data) {
            for (Object category: categories) {
                if (CustomEqualsUtil.customEquals(itr.get(xCoord), category, maKieuDuLieu)) {
                    int index = 0;
                    for (String line: lines) {
                        allSeries.get(index).getData().add(itr.get(line));
                        index++;
                    }
                    break;
                }
            }
        }

        for (ChartResponse e: allSeries) {
            for (CotDuLieu row: rows) {
                if (row.getTEN_COT().equals(e.getName())) {
                    e.setName(row.getMO_TA());
                }
            }
        }

        String text;
        List<CotDuLieu> cotDuLieusFilterByXCoord = rows.stream().filter(e -> e.getTEN_COT().equals(xCoord)).toList();
        if (cotDuLieusFilterByXCoord.isEmpty()) {
            text = xCoord;
        } else {
            text = cotDuLieusFilterByXCoord.get(0).getMO_TA();
        }
        xAxis.setTitle(
                new Title(
                        text,Align.CENTER, null, null, null, null, getXAxisTitleStyle(), Position.BOTTOM
                )
        );
        xAxis.setCategories(categories.stream().map(e -> mapLabelValue(e, xCoord, rows)));

        return new ChartOptions(
                chart,
                allSeries,
                new EmptyJsonBody(),
                title,
                xAxis,
                new EmptyJsonBody(),
                Arrays.stream(chartEntity.getMauSac().split(";")).toList(),
                new EmptyJsonBody(),
                new EmptyJsonBody(),
                new DataLabels(),
                new Marker()
        );
    }

    private Object mapLabelValue(Object value, String colName, List<CotDuLieu> rows) {
        String kieuDuLieu = "";
        for (CotDuLieu e: rows) {
            if (e.getTEN_COT().equals(colName)) {
                kieuDuLieu = e.getMA_KIEU_DLIEU();
            }
        }
        if (kieuDuLieu.isEmpty() || kieuDuLieu.equals(DataType.KDL_1) || kieuDuLieu.equals(DataType.KDL_2)) {
            return value;
        } else if (kieuDuLieu.equals(DataType.KDL_3)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format((Date) value);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            return dateFormat.format((Date) value);
        }
    }

    private Style getTitleStyle() {
        return new Style("1.25rem", "bold", "Roboto", "#000000");
    }

    private Style getXAxisTitleStyle() {
        return new Style("14px", "bold", "Roboto", "#263238");
    }
}
