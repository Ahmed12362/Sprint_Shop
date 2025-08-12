package com.example.Sprint.service.product;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.exception.AlreadyExists;
import com.example.Sprint.exception.ResourceNotFound;
import com.example.Sprint.model.Category;
import com.example.Sprint.model.Product;
import com.example.Sprint.repository.CategoryRepository;
import com.example.Sprint.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
//    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        productRepository.findByName(productDto.getName()).ifPresent(
                product -> {
                    throw new AlreadyExists("Product Already Exists");
                }
        );
        Category category = categoryRepository.findByName(productDto.getCategoryName())
                .orElseThrow(()->new ResourceNotFound("Category not found"));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        log.info("Product with id {}",productDto.getPrice());
        return productDto;
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
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Product not found"));
        Category category = categoryRepository.findByName(updatedProduct.getCategoryName())
                .orElseThrow(()->new ResourceNotFound("Category not found"));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return updatedProduct;
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
