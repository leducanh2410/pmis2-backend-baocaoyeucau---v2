package com.pmis.repository;

import com.pmis.models.entity.ConnectionConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionConfigRepository extends JpaRepository<ConnectionConfig, String> {
    List<ConnectionConfig> findByORGID(String ORGID);
    Optional<ConnectionConfig> findByMAKETNOI(String MAKETNOI);
    void deleteByMAKETNOI(String MAKETNOI);
}
