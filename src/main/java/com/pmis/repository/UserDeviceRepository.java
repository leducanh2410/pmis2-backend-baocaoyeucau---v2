package com.pmis.repository;

import com.pmis.models.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    boolean existsByUserIdAndDeviceId(String userId, String deviceId);
}
