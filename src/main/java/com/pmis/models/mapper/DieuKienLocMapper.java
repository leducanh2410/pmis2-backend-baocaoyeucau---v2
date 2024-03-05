package com.pmis.models.mapper;

import com.pmis.models.model.data_exploitation.DieuKienLoc;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DieuKienLocMapper implements RowMapper<DieuKienLoc> {
    @Override
    public DieuKienLoc mapRow(ResultSet rs, int rowNum) throws SQLException {
        DieuKienLoc dieuKienLoc = new DieuKienLoc();
        dieuKienLoc.setMA_LOC(rs.getString("MA_LOC"));
        dieuKienLoc.setGIA_TRI_LOC(rs.getString("GIA_TRI_LOC"));
        dieuKienLoc.setSTT(rs.getInt("STT"));
        dieuKienLoc.setMA_COT(rs.getString("MA_COT"));
        dieuKienLoc.setTEN_COT(rs.getString("TEN_COT"));
        dieuKienLoc.setLOAI_DKIEN(rs.getString("LOAI_DKIEN"));
        dieuKienLoc.setMA_KIEU_DLIEU(rs.getString("MA_KIEU_DLIEU"));
        dieuKienLoc.setMA_DULIEU(rs.getString("MA_DULIEU"));
        dieuKienLoc.setNHOM_DKIEU(rs.getString("NHOM_DKIEU"));
        return dieuKienLoc;
    }
}
