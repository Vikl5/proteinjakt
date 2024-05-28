package com.app.proteinjakt.repository;

import com.app.proteinjakt.dto.ProteinProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProteinProduct, Long> {
}
