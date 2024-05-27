package com.app.proteinjakt.repository;

import com.app.proteinjakt.dto.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
