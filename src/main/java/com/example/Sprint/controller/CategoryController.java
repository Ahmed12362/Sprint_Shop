package com.example.Sprint.controller;

import com.example.Sprint.dto.CategoryDto;
import com.example.Sprint.response.BaseResponse;
import com.example.Sprint.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto result = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(new BaseResponse("Added" , result));
    }


    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAll(){
        List<CategoryDto> result = categoryService.getAllCategory();
        return ResponseEntity.ok(new BaseResponse("Loaded" , result));
    }
}
