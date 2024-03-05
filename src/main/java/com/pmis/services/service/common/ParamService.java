package com.pmis.services.service.common;

import com.pmis.models.model.Api_Service_Input;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParamService {
    public boolean checkParameterInput(List<Map<String, Object>> configParameter, List<Api_Service_Input> userParameter) {
        boolean bCheck = false;
        try {
            for (Map<String, Object> obj : configParameter) {
                if (obj.containsKey("DEFAULT_VALUE_SYSTEM") && (Boolean) obj.get("DEFAULT_VALUE_SYSTEM")) {
                    if (!obj.containsKey("DEFAULT_VALUE_NAME") || !getLstParamSystem().containsKey(obj.get("DEFAULT_VALUE_NAME"))) {
                        return false;
                    }
                } else {
                    bCheck = false;
                    for (Api_Service_Input userObj : userParameter) {
                        if (obj.get("API_SERVICE_INPUTNAME").equals(userObj.getName())) {
                            bCheck = true;
                            break;
                        }
                    }
                    if (!bCheck) {
                        return false;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Map<String, String> getLstParamSystem() {
        Map<String, String> mResult = new HashMap<String, String>();
        mResult.put("USERID", "Mã người dùng đăng nhập service");
        return mResult;
    }
}
