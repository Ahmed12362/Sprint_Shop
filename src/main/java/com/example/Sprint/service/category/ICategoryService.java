package com.example.Sprint.service.category;

import com.example.Sprint.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategory();
}
