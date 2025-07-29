package com.billing.Bill.Generation.App.service;

import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.model.Category;
import com.billing.Bill.Generation.App.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public UserResponse<?> addListOfCategories(List<Category> categoryList){
        categoryRepo.saveAll(categoryList);
        return new UserResponse<>(null,"All Categories Added Successfully",true);
    }

    public UserResponse<?> addCategory(Category category){
        categoryRepo.save(category);
        return new UserResponse<>(null,"Category Added Successfully",true);
    }
}
