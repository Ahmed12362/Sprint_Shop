package com.example.Sprint.service.category;

import com.example.Sprint.dto.CategoryDto;
import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.exception.AlreadyExists;
import com.example.Sprint.model.Category;
import com.example.Sprint.model.Product;
import com.example.Sprint.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        categoryRepository.findByName(categoryDto.getName())
                .ifPresent(c -> {
                    throw new AlreadyExists("Category Already Exists");
                });
        categoryDto.setProductDtoList(new ArrayList<>());
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setProductList(categoryDto.getProductDtoList()
                .stream()
                .map(productDto -> {
                    Product product = new Product();
                    product.setName(productDto.getName());
                    product.setPrice(productDto.getPrice());
                    product.setCategory(category);
                    return product;
                })
                .collect(Collectors.toList())
        );
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setProductDtoList(category.getProductList()
                    .stream()
                    .map(product -> {
                        ProductDto productDto = new ProductDto();
                        productDto.setName(product.getName());
                        productDto.setPrice(product.getPrice());
                        productDto.setCategoryName(category.getName());
                        return productDto;
                    }).collect(Collectors.toList()));
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
}
