package tdt.micronaut.service;

import tdt.micronaut.entity.Product;
import tdt.micronaut.service.input.CreateProductInput;
import tdt.micronaut.service.input.UpdateProductInput;
import tdt.micronaut.service.output.ProductListOutput;
import tdt.micronaut.service.output.ProductOutput;

public interface ProductService {

  ProductOutput getById(Long id);

  ProductListOutput paginate(Integer page, Integer size);

  ProductOutput createProduct(CreateProductInput input);

  ProductOutput updateProduct(Long id, UpdateProductInput input);

  void deleteProduct(Long id);

  ProductListOutput search(Integer page, Integer size, String searchText);
}
