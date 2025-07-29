package com.billing.Bill.Generation.App.controller;

import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.model.Category;
import com.billing.Bill.Generation.App.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //add list of categories
    @PostMapping("add-all-category")
    public UserResponse<?> addAllCategory(@RequestBody List<Category> categoryList){
        try{
            return categoryService.addListOfCategories(categoryList);
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }

    //add single category
    @PostMapping("add-category")
    public UserResponse<?> addCategory(@RequestBody Category category){
        try{
            return categoryService.addCategory(category);
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }
}
