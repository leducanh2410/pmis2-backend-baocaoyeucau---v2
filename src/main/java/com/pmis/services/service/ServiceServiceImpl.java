package com.pmis.services.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> getServiceInfo(String serviceId) {
        String queryString = "SELECT * FROM [API_SERVICE] A LEFT JOIN [API_LST_OUTPUT] B ON (A.API_SERVICE_OUTPUTID=B.API_SERVICE_OUTPUTID)\n" + "WHERE A.API_SERVICEID=:serviceid";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("serviceid", serviceId);
            Map<String, Object> obj = jdbcTemplate.queryForMap(queryString, parameters);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getServiceInputInfo(String serviceId) {
        String queryString = "SELECT * FROM [API_SERVICE_INPUT] A LEFT JOIN [API_LST_INPUT] B ON (A.API_SERVICE_INPUTID = B.API_SERVICE_INPUTID)\n" + "WHERE A.API_SERVICEID=:serviceid";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("serviceid", serviceId);
            List<Map<String, Object>> obj = jdbcTemplate.queryForList(queryString, parameters);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean checkAuthServiceInfo(String serviceId, String userId) {
        String queryString = "SELECT TOP 1 B.FUNCTIONID,B.R_INSERT R_INSERT_USER\n" + ",B.R_EDIT R_EDIT_USER,B.R_DELETE R_DELETE_USER\n" + ",C.API_SERVICEID,\n" + " (CASE WHEN C.R_INSERT=1 THEN 1 ELSE 0 END) R_INSERT_REQ\n" + ",(CASE WHEN C.R_EDIT=1 THEN 1 ELSE 0 END) R_EDIT_REQ,\n" + "(CASE WHEN C.R_DELETE=1 THEN 1 ELSE 0 END) R_DELETE_REQ FROM (\n" + "SELECT FUNCTIONID,\n" + "MAX(CASE WHEN R_INSERT=1 THEN 1 ELSE 0 END) R_INSERT,\n" + "MAX(CASE WHEN R_EDIT=1 THEN 1 ELSE 0 END) R_EDIT,\n" + "MAX(CASE WHEN R_DELETE=1 THEN 1 ELSE 0 END) R_DELETE FROM \n" + "(\n" + "SELECT FUNCTIONID,R_INSERT,R_EDIT,R_DELETE FROM Q_PQFUNCTION_ROLE\n" + "WHERE ROLEID IN (\n" + "SELECT ROLEID FROM Q_USER_ROLE WHERE USERID=:userId)\n" + "UNION ALL\n" + "SELECT FUNCTIONID,R_INSERT,R_EDIT,R_DELETE FROM Q_PQFUNCTION_USER WHERE USERID=:userId\n" + "UNION ALL\n" + "SELECT FUNCTIONID,1 R_INSERT,1 R_EDIT,1 R_DELETE FROM Q_FUNCTION WHERE ISPUPLIC=1 AND [ENABLE]=1\n" + ") A\n" + "GROUP BY FUNCTIONID) B, Q_FUNCTION_SERVICE C\n" + "WHERE B.FUNCTIONID=C.FUNCTIONID\n" + "AND C.API_SERVICEID=:serviceid";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("serviceid", serviceId).addValue("userId", userId);
            Map<String, Object> obj = jdbcTemplate.queryForMap(queryString, parameters);
            if (obj == null || obj.isEmpty()) {
                return false;
            } else {
                if ("1".equals(obj.get("R_INSERT_REQ").toString()) && "0".equals(obj.get("R_INSERT_USER").toString())) {
                    return false;
                }
                if ("1".equals(obj.get("R_EDIT_REQ").toString()) && "0".equals(obj.get("R_EDIT_USER").toString())) {
                    return false;
                }
                if ("1".equals(obj.get("R_DELETE_REQ").toString()) && "0".equals(obj.get("R_DELETE_USER").toString())) {
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * * * @param service * @param parameter * @return
     */
    @Override
    public Map<String, Object> execService(Map<String, Object> service, MapSqlParameterSource parameter) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        Object dataResult = null;
        String callProcedure = "";
        switch (service.get("API_SERVICE_TYPEID").toString()) {
            case "PROCEDURE":
                callProcedure = "execute ";
                break;
            case "FUNCTION":
                callProcedure = "SELECT ";
                break;
            case "SQL":
                callProcedure = "";
                break;
        }
        try {
            switch (service.get("API_SERVICE_OUTPUTID").toString()) {
                case "EXEC_NORESULT":
                    dataResult = jdbcTemplate.update(callProcedure + service.get("API_SERVICE_DATA").toString(), parameter);
                    break;
                case "JSON":
                    dataResult = jdbcTemplate.queryForMap(callProcedure + service.get("API_SERVICE_DATA").toString(), parameter);
                    break;
                case "JSON_LIST":
                    dataResult = jdbcTemplate.queryForList(callProcedure + service.get("API_SERVICE_DATA").toString(), parameter);
                    break;
                case "OBJECT":
                    dataResult = jdbcTemplate.queryForObject(callProcedure + service.get("API_SERVICE_DATA").toString(), parameter, Object.class);
                    break;
                case "OBJECT_LIST":
                    dataResult = jdbcTemplate.queryForList(callProcedure + service.get("API_SERVICE_DATA").toString(), parameter, Object.class);
                    break;
            }
            mapResult.put("Status", 1);
            mapResult.put("Message", "Thực thi lệnh thành công");
            mapResult.put("data", dataResult);
        } catch (Exception e) {
            e.printStackTrace();
            mapResult.put("Status", 0);
            mapResult.put("Message", "Xảy ra lỗi khi thực thi câu lệnh");
        }
        return mapResult;
    }

    @Override
    public Map<String, Object> getConnectInfoByTable(String MA_BANG) {
        String queryString = "SELECT * FROM BCTM_CAU_HINH_KET_NOI\n"
                + "WHERE MA_KETNOI IN (select MA_KETNOI from BCTM_BANG_DULIEU WHERE MA_BANG = :MA_BANG )";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("MA_BANG", MA_BANG);
            Map<String, Object> obj = jdbcTemplate.queryForMap(queryString, parameters);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
