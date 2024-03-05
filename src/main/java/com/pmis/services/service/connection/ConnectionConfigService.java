package com.pmis.services.service.connection;

import com.pmis.models.entity.ConnectionConfig;
import com.pmis.payload.dto.ConnectionConfigDTO;
import com.pmis.repository.ConnectionConfigRepository;
import com.pmis.util.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConnectionConfigService {
    @Autowired
    private ConnectionConfigRepository repository;

    @Transactional
    public String save(ConnectionConfigDTO connectionConfigDTO, boolean isUpdate) {
        try {
            if (isUpdate) {
                Optional<ConnectionConfig> oldConn = repository.findByMAKETNOI(connectionConfigDTO.MA_KETNOI);
                if (oldConn.isEmpty()) {
                    return null;
                }
                ConnectionConfig connectionConfig = convertToEntity(connectionConfigDTO);
                if (connectionConfig.getMATKHAU() == null || !connectionConfig.getMATKHAU().isEmpty()) {
                    connectionConfig.setMATKHAU(Util.encrypt(connectionConfig.getMATKHAU()));
                } else {
                    connectionConfig.setMATKHAU(oldConn.get().getMATKHAU());
                }
                repository.save(connectionConfig);
                return connectionConfig.getMAKETNOI();
            } else {
                ConnectionConfig connectionConfig = convertToEntity(connectionConfigDTO);
                String id = UUID.randomUUID().toString();
                connectionConfig.setMAKETNOI(id);
                if (connectionConfig.getMATKHAU() == null || !connectionConfig.getMATKHAU().isEmpty()) {
                    connectionConfig.setMATKHAU(Util.encrypt(connectionConfig.getMATKHAU()));
                }
                repository.save(connectionConfig);
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ConnectionConfigDTO> findByORGID(String ORGID) {
        List<ConnectionConfig> connectionConfigs = repository.findByORGID(ORGID);
        return connectionConfigs
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ConnectionConfigDTO> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "USERCRDTIME"))
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ConnectionConfigDTO findByMAKETNOI(String MAKETNOI) {
        Optional<ConnectionConfig> connectionConfigOptional = repository.findByMAKETNOI(MAKETNOI);
        return connectionConfigOptional.map(this::convertToDTO).orElse(null);
    }

    public String findPasswordByMAKETNOI(String MAKETNOI) {
        Optional<ConnectionConfig> connectionConfigOptional = repository.findByMAKETNOI(MAKETNOI);
        return connectionConfigOptional.map(ConnectionConfig::getMATKHAU).orElse(null);
    }

    @Transactional
    public Integer deleteByMAKETNOI(String MAKETNOI) {
        try {
            repository.deleteByMAKETNOI(MAKETNOI);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private ConnectionConfigDTO convertToDTO(ConnectionConfig connectionConfig) {
        return new ConnectionConfigDTO(
                connectionConfig.getMAKETNOI(),
                connectionConfig.getTENKETNOI(),
                connectionConfig.getIP(),
                connectionConfig.getPORT(),
                connectionConfig.getORGID(),
                connectionConfig.getTENDANGNHAP(),
//                connectionConfig.getMATKHAU(),
                "",
                connectionConfig.getTENCSDL(),
                connectionConfig.getUSERCRID(),
                connectionConfig.getUSERCRDTIME(),
                connectionConfig.getUSERMDFID(),
                connectionConfig.getUSERMDFDTIME()
        );
    }

    private ConnectionConfig convertToEntity(ConnectionConfigDTO connectionConfigDTO) {
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setMAKETNOI(connectionConfigDTO.MA_KETNOI);
        connectionConfig.setTENKETNOI(connectionConfigDTO.TEN_KETNOI);
        connectionConfig.setIP(connectionConfigDTO.IP);
        connectionConfig.setPORT(connectionConfigDTO.PORT);
        connectionConfig.setORGID(connectionConfigDTO.ORGID);
        connectionConfig.setTENDANGNHAP(connectionConfigDTO.TEN_DANG_NHAP);
        connectionConfig.setMATKHAU(connectionConfigDTO.MAT_KHAU);
        connectionConfig.setTENCSDL(connectionConfigDTO.TEN_CSDL);
        connectionConfig.setUSERCRID(connectionConfigDTO.USER_CR_ID);
        connectionConfig.setUSERCRDTIME(connectionConfigDTO.USER_CR_DTIME);
        connectionConfig.setUSERMDFID(connectionConfigDTO.USER_MDF_ID);
        connectionConfig.setUSERMDFDTIME(connectionConfigDTO.USER_MDF_DTIME);
        return connectionConfig;
    }
}
