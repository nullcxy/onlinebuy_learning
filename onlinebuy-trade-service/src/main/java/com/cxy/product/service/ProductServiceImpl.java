package com.cxy.product.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxy.common.constants.Constants;
import com.cxy.product.dao.CategoryMapper;
import com.cxy.product.dao.ProductMapper;
import com.cxy.product.entity.Category;
import com.cxy.product.entity.Product;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    int PAGE_SIZE = 15; //默认分页大小

    int PAGE_NUMBER = 0; //默认当前分页

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JestHttpClient esClient;

    @Override
    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_CATEGORY)
    public List<Category> listCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Product> listProduct(Long categoryId) {
        //应该走es
        return productMapper.selectByCategory(categoryId);
    }

    @Override
    public List<Product> searchProduct(int pageNumber, int pageSize, String searchContent) throws IOException {

        if(pageSize==0) {
            pageSize = PAGE_SIZE;
        }
        if(pageNumber<0) {
            pageNumber = PAGE_NUMBER;
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("spu_name",searchContent))
                .must(QueryBuilders.matchQuery("status",1)))
                .from(pageSize)
                .size(pageNumber);
        searchSourceBuilder.highlight()
                .field("spu_name")
                .preTags("<em>").postTags("</em>")
                .fragmentSize(200);
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex("mama-buy-trade")
                .build();
        SearchResult response = esClient.execute(search);
//        List<Product> productList =  response.getSourceAsObjectList(Product.class,true);
//        List<SearchResult.Hit<Product, Void>> hitslist = response.getHits(Product.class);
        String jsonString = response.getJsonString();
        List<Product> productList = parseResult(jsonString);

        return productList;

    }


    @Override
    @Cacheable(cacheNames = Constants.CACHE_PRODUCT_DETAIL,key = "#id")
    public Product productDetail(Long id) {

        Product product = productMapper.selectByPrimaryKeyWithSku(id);

        return product;
    }

    private List<Product> parseResult(String jsonString) {
        JSONArray jsonArray = JSON.parseObject(jsonString).getJSONObject("hits").getJSONArray("hits");
        List<Product> productList = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject productObj = jsonObject.getJSONObject("_source");
            Product product = new Product();
            product.setId(productObj.getLongValue("id"));
            product.setCategoryId(productObj.getLongValue("category_id"));
            product.setBrandId(productObj.getLongValue("brand_id"));
            product.setSpuName(productObj.getString("spu_name"));
            product.setPrice(productObj.getBigDecimal("price"));

            productList.add(product);
        }
        return productList;
    }


}
