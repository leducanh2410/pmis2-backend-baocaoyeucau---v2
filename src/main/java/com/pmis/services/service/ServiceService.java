package com.pmis.services.service;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public interface ServiceService {

    Map<String, Object> getServiceInfo(String serviceId);

    List<Map<String, Object>> getServiceInputInfo(String serviceId);

    Boolean checkAuthServiceInfo(String serviceId, String userId);

    Map<String, Object> execService(Map<String, Object> service, MapSqlParameterSource parameter);

    Map<String, Object> getConnectInfoByTable(String MA_BANG);
}
