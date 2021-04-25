package tdt.micronaut.elasticsearch;

import tdt.micronaut.entity.Product;
import tdt.micronaut.service.output.ProductListOutput;
import tdt.micronaut.service.output.ProductOutput;

public interface ProductElasticSearchService {

  void createProduct(Product product);

  ProductOutput getById(Long productId);

  void updateProduct(Product product);

  void deleteProduct(Long productId);

  ProductListOutput search(Integer page, Integer size, String searchText);

}
