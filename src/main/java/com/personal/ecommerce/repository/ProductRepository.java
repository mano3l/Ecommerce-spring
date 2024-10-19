package com.personal.ecommerce.repository;

import com.personal.ecommerce.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends ListPagingAndSortingRepository<Product, UUID>, JpaRepository<Product, UUID> {
    Page<Product> findByCategory_Id(@NonNull UUID id, Pageable pageable);
}