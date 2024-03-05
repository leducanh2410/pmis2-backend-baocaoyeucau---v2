package com.pmis.services.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmis.models.model.Api_Service_Input;
import com.pmis.security.services.SecurityUtils;
import com.pmis.util.Util;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParamLoginService {
    public MapSqlParameterSource buildParameterLogin(
            List<Map<String, Object>> configParameter,
            List<Api_Service_Input> userParameter
    ) {
        MapSqlParameterSource parametersResult = new MapSqlParameterSource();
        String userId = SecurityUtils.getPrincipal().getUserId();
        for (Map<String, Object> obj : configParameter) {
            if (obj.containsKey("DEFAULT_VALUE_SYSTEM") && (Boolean) obj.get("DEFAULT_VALUE_SYSTEM")) {
                switch (obj.get("DEFAULT_VALUE_NAME").toString()) {
                    case "USERID":
                        parametersResult.addValue(obj.get("API_SERVICE_INPUTNAME").toString(), userId);
                        break;
                }
            } else {
                for (Api_Service_Input userObj : userParameter) {
                    if (obj.get("API_SERVICE_INPUTNAME").equals(userObj.getName())) {
                        switch (obj.get("API_SERVICE_INPUT_TYPEID").toString()) {
                            case "STR_PASS":
                                parametersResult.addValue(obj.get("API_SERVICE_INPUTNAME").toString(), userObj.getValue() != null ? Util.encrypt(userObj.getValue().toString()) : "");
                                break;
                            case "JSON_STR":
                                try {
                                    parametersResult.addValue(obj.get("API_SERVICE_INPUTNAME").toString(), new ObjectMapper().writeValueAsString(userObj.getValue()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                parametersResult.addValue(obj.get("API_SERVICE_INPUTNAME").toString(), userObj.getValue());
                                break;
                        }

                    }
                }

            }
        }
        return parametersResult;
    }
}
