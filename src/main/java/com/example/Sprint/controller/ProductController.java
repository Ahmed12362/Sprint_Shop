package com.example.Sprint.controller;

import com.example.Sprint.dto.ProductDto;
import com.example.Sprint.response.BaseResponse;
import com.example.Sprint.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
   private final ProductService productService;

   @PostMapping("/add")
    public ResponseEntity<BaseResponse> addProduct(@RequestBody ProductDto productDto){
       ProductDto result = productService.addProduct(productDto);
       return ResponseEntity.ok(new BaseResponse("Added",result));
   }
}
