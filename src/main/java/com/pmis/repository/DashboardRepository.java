package com.pmis.repository;

import com.pmis.models.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, String> {
    List<Dashboard> findByUserId(String userId);
}
