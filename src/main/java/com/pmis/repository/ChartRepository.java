package com.pmis.repository;

import com.pmis.models.entity.Chart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartRepository extends JpaRepository<Chart, String> {
}
