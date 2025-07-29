package com.billing.Bill.Generation.App.repository;

import com.billing.Bill.Generation.App.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,String> {
}
