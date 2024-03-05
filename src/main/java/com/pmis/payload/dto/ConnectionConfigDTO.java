package com.pmis.payload.dto;

import java.util.Date;

public class ConnectionConfigDTO {
    public String MA_KETNOI;
    public String TEN_KETNOI;
    public String IP;
    public String PORT;
    public String ORGID;
    public String TEN_DANG_NHAP;
    public String MAT_KHAU;
    public String TEN_CSDL;
    public String USER_CR_ID;
    public Date USER_CR_DTIME;
    public String USER_MDF_ID;
    public Date USER_MDF_DTIME;

    public ConnectionConfigDTO() {}

    public ConnectionConfigDTO(
            String MA_KETNOI,
            String TEN_KETNOI,
            String IP, String PORT,
            String ORGID,
            String TEN_DANG_NHAP,
            String MAT_KHAU,
            String TEN_CSDL,
            String USER_CR_ID,
            Date USER_CR_DTIME,
            String USER_MDF_ID,
            Date USER_MDF_DTIME) {
        this.MA_KETNOI = MA_KETNOI;
        this.TEN_KETNOI = TEN_KETNOI;
        this.IP = IP;
        this.PORT = PORT;
        this.ORGID = ORGID;
        this.TEN_DANG_NHAP = TEN_DANG_NHAP;
        this.MAT_KHAU = MAT_KHAU;
        this.TEN_CSDL = TEN_CSDL;
        this.USER_CR_ID = USER_CR_ID;
        this.USER_CR_DTIME = USER_CR_DTIME;
        this.USER_MDF_ID = USER_MDF_ID;
        this.USER_MDF_DTIME = USER_MDF_DTIME;
    }
}
