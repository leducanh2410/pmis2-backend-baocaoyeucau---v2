package com.pmis.services.service;

import com.pmis.constant.DataType;
import com.pmis.models.model.data_exploitation.CotDuLieu;
import com.pmis.payload.request.BarChartSeriesRequest;
import com.pmis.payload.request.ChartRequest;
import com.pmis.payload.response.ChartResponse;
import com.pmis.util.CustomEqualsUtil;
import com.pmis.util.Util;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChartService {
    public List<ChartResponse> getChartSeries(
            List<Map<String, Object>> data,
            List<CotDuLieu> rows,
            ChartRequest request
    ) {
        String maKieuDuLieu = "";
        for (CotDuLieu e: rows) {
            if (request.getxCoordinate().equals(e.getTEN_COT())) {
                maKieuDuLieu = e.getMA_KIEU_DLIEU();
            }
        }

        List<ChartResponse> responses = new ArrayList<>();
        for (String line: request.getLines()) {
            ChartResponse chartResponse = new ChartResponse(line, new ArrayList<>());
            responses.add(chartResponse);
        }

//        for (Object category: request.getCategories()) {
//            for (String line: request.getLines()) {
//                Object val = null;
//                for (Map<String, Object> itr: data) {
//                    if (customEquals(itr.get(request.getxCoordinate()), category, maKieuDuLieu)) {
//                        val = itr.get(line);
//                        break;
//                    }
//                }
//                for (ChartResponse series: responses) {
//                    if (series.getName().equals(line)) {
//                        series.getData().add(val);
//                    }
//                }
//            }
//        }
        for (Map<String, Object> itr: data) {
            for (Object category: request.getCategories()) {
                if (CustomEqualsUtil.customEqualsFrontEnd(itr.get(request.getxCoordinate()), category, maKieuDuLieu)) {
                    int index = 0;
                    for (String line: request.getLines()) {
                        responses.get(index).getData().add(itr.get(line));
                        index++;
                    }
                    break;
                }
            }
        }

        for (ChartResponse e: responses) {
            for (CotDuLieu row: rows) {
                if (row.getTEN_COT().equals(e.getName())) {
                    e.setName(row.getMO_TA());
                }
            }
        }

        return responses;
    }

    public List<ChartResponse> getBarChartSeries(
            List<Map<String, Object>> data,
            List<CotDuLieu> rows,
            BarChartSeriesRequest request
    ) {
        String maKieuDuLieu = "";
        if (request.getxCol() != null && !request.getxCol().isEmpty()) {
            for (CotDuLieu e: rows) {
                if (request.getxCol().equals(e.getTEN_COT())) {
                    maKieuDuLieu = e.getMA_KIEU_DLIEU();
                }
            }
        }

        List<ChartResponse> allSeries = new ArrayList<>();
        if (request.getxCol() != null && !request.getxCol().isEmpty()) {
            for (String e: request.getColumns()) {
                ChartResponse chartResponse = new ChartResponse(e, new ArrayList<>());
                allSeries.add(chartResponse);
            }
            for (ChartResponse series: allSeries) {
                List<Object> seriesData = new ArrayList<>();
                for (Object e: request.getCategories()) {
                    AtomicReference<Double> sum = new AtomicReference<>(0.0);
                    if (!data.isEmpty()) {
                        String finalMaKieuDuLieu = maKieuDuLieu;
                        List<Map<String, Object>> vals = data.stream().filter(ee -> ee.get(request.getxCol()) != null && CustomEqualsUtil.customEqualsFrontEnd(ee.get(request.getxCol()), e, finalMaKieuDuLieu)).toList();
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
        } else {
            for (String e: request.getColumns()) {
                ChartResponse chartResponse = new ChartResponse(e, new ArrayList<>());
                chartResponse.getData().add(data.get(0).get(e));
                allSeries.add(chartResponse);
            }
        }
        allSeries.forEach(e -> {
            List<CotDuLieu> cotDuLieusTmp = rows.stream().filter(ee -> ee.getTEN_COT().equals(e.getName())).toList();
            if (!cotDuLieusTmp.isEmpty()) {
                e.setName(cotDuLieusTmp.get(0).getMO_TA());
            }
        });
        return allSeries;
    }
}
