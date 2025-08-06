package com.example.Sprint.service.product;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.model.Product;

import java.util.List;

public interface IProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getAllProduct();
    ProductDto getProduct(Long id);
    ProductDto updateProduct(Long id , ProductDto updatedProduct);
    String deleteProduct(Long id);
    List<ProductDto> getTop5Product();
}