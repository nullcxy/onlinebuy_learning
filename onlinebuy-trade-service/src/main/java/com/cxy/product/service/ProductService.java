package com.cxy.product.service;

import com.cxy.product.entity.Category;
import com.cxy.product.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Category> listCategory();

    List<Product> listProduct(Long categoryId);

    List<Product> searchProduct(int pageNumber, int pageSize, String searchContent) throws IOException;

    Product productDetail(Long id);
}
