package com.pmis.services.service.common;

import com.pmis.models.model.Api_Service_Input;
import com.pmis.payload.dto.ConnectionConfigDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConnectionParamService {
    public ConnectionConfigDTO convertParamsToConnDTO(List<Api_Service_Input> params) {
        Map<String, Object> mapParams = new HashMap<>();
        params.forEach(e -> {
            mapParams.put(e.getName(), e.getValue());
        });
        Object maKetNoi = mapParams.get("MA_KETNOI");
        Object tenKetNoi = mapParams.get("TEN_KETNOI");
        Object ip = mapParams.get("IP");
        Object port = mapParams.get("PORT");
        Object orgid = mapParams.get("ORGID");
        Object username = mapParams.get("TEN_DANG_NHAP");
        Object pwd = mapParams.get("MAT_KHAU");
        Object dbname = mapParams.get("TEN_CSDL");
        Object userCreateId = mapParams.get("USER_CR_ID");
        Object userMdfId = mapParams.get("USER_MDF_ID");
        return new ConnectionConfigDTO(
                maKetNoi != null ? maKetNoi.toString() : null,
                tenKetNoi != null ? tenKetNoi.toString() : null,
                ip != null ? ip.toString() : null,
                port != null ? port.toString() : null,
                orgid != null ? orgid.toString() : null,
                username != null ? username.toString() : null,
                pwd != null ? pwd.toString() : null,
                dbname != null ? dbname.toString() : null,
                userCreateId != null ? userCreateId.toString() : null,
                null,
                userMdfId != null ? userMdfId.toString() : null,
                null
        );
    }
}
