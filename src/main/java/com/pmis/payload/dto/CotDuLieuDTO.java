package com.pmis.payload.dto;

public class CotDuLieuDTO {
    public String MA_DULIEU_CTIET;
    public String MA_COT;
    public Integer STT;
    public String TEN_COT;
    public String MO_TA;
    public String MA_KIEU_DLIEU;
    public String SORT;
    public String FORMAT;
    public Integer VIEW;

    public CotDuLieuDTO() {
    }

    public CotDuLieuDTO(String MA_DULIEU_CTIET, String MA_COT, Integer STT, String TEN_COT, String MO_TA, String MA_KIEU_DLIEU, String SORT, String FORMAT, Integer VIEW) {
        this.MA_DULIEU_CTIET = MA_DULIEU_CTIET;
        this.MA_COT = MA_COT;
        this.STT = STT;
        this.TEN_COT = TEN_COT;
        this.MO_TA = MO_TA;
        this.MA_KIEU_DLIEU = MA_KIEU_DLIEU;
        this.SORT = SORT;
        this.FORMAT = FORMAT;
        this.VIEW = VIEW;
    }
}
