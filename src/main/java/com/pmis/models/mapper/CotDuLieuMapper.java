package com.pmis.models.mapper;

import com.pmis.models.model.data_exploitation.CotDuLieu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CotDuLieuMapper implements RowMapper<CotDuLieu> {
    @Override
    public CotDuLieu mapRow(ResultSet rs, int rowNum) throws SQLException {
        CotDuLieu cotDuLieu = new CotDuLieu();
        cotDuLieu.setMA_COT(rs.getString("MA_COT"));
        cotDuLieu.setTEN_COT(rs.getString("TEN_COT"));
        cotDuLieu.setFORMAT(rs.getString("FORMAT"));
        cotDuLieu.setSORT(rs.getString("SORT"));
        cotDuLieu.setSTT(rs.getInt("STT"));
        cotDuLieu.setVIEW(rs.getInt("VIEW"));
        cotDuLieu.setMA_DULIEU_CTIET(rs.getString("MA_DULIEU_CTIET"));
        cotDuLieu.setMA_KIEU_DLIEU(rs.getString("MA_KIEU_DLIEU"));
        cotDuLieu.setMO_TA(rs.getString("MO_TA"));
        return cotDuLieu;
    }
}
