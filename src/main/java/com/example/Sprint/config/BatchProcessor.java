package com.example.Sprint.config;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.model.Category;
import com.example.Sprint.model.Product;
import com.example.Sprint.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchProcessor implements ItemProcessor<ProductDto , Product> {
    private final CategoryRepository categoryRepository;
    @Override
    public Product process(ProductDto item) throws Exception {
       Category category = categoryRepository.findByName(item.getCategoryName())
               .orElseGet(()->{
                   Category category1 = new Category();
                   category1.setName(item.getCategoryName().toLowerCase());
                   return categoryRepository.save(category1);
               });
       Product product = new Product();
       product.setName(item.getName());
       product.setPrice(item.getPrice());
       product.setCategory(category);
        return product;
    }
}
