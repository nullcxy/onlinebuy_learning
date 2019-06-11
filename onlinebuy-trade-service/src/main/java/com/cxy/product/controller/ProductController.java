package com.cxy.product.controller;

import com.cxy.common.resp.ApiResult;
import com.cxy.product.entity.Category;
import com.cxy.product.entity.PageSearch;
import com.cxy.product.entity.Product;
import com.cxy.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {


    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    
    /**
     *   列出分类
     */
    @RequestMapping("/category")
    public ApiResult<List<Category>> listCategory(){

        ApiResult<List<Category>> result = new ApiResult<>(200,"查询分类成功");

        List<Category> list =  productService.listCategory();

        result.setData(list);
        return result;
    }

    /**
     *  列出分类下产品
     */
    @RequestMapping("/list/{categoryId}")
    public ApiResult<List<Product>> listProduct(@PathVariable Long categoryId){

        ApiResult<List<Product>> result = new ApiResult<>(200,"查询分类下产品成功");

        List<Product> list =  productService.listProduct(categoryId);

        result.setData(list);
        return result;
    }

    /**
     * 检索商品
     */
    @RequestMapping("/search")
    public ApiResult<List<Product>> searchProduct(@RequestBody PageSearch pageSearch) throws IOException {

        ApiResult<List<Product>> result = new ApiResult<>(200,"查询分类下产品成功");

        List<Product> list =  productService.searchProduct(pageSearch.getPageNumber(),
                pageSearch.getPageSize(),pageSearch.getSearchContent());

        result.setData(list);
        return result;

    }

    @RequestMapping("/detail/{id}")
    public ApiResult<Product> productDetail(@PathVariable Long id){

        ApiResult<Product> result = new ApiResult<>(200,"获取商品详情成功");

        Product product =  productService.productDetail(id);

        result.setData(product);

        return result;

    }

}
