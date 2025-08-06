package com.example.Sprint.service.product;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.exception.ResourceNotFound;
import com.example.Sprint.model.Product;
import com.example.Sprint.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> productList =  productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : productList){
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryName(product.getCategory().getName());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Product Not Found"));
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto updatedProduct) {
        return null;
    }

    @Override
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Product Not Found"));
        productRepository.delete(product);
        return "Done";
    }

    @Override
    public List<ProductDto> getTop5Product() {
        List<Product> productList = productRepository.findTop5ByOrderByPriceDesc();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : productList){
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryName(product.getCategory().getName());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
