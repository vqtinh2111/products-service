package tdt.micronaut.service;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import java.util.Optional;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import tdt.micronaut.common.States;
import tdt.micronaut.elasticsearch.ProductElasticSearchService;
import tdt.micronaut.entity.Product;
import tdt.micronaut.repository.ProductRepository;
import tdt.micronaut.service.input.CreateProductInput;
import tdt.micronaut.service.input.UpdateProductInput;
import tdt.micronaut.service.output.ProductListOutput;
import tdt.micronaut.service.output.ProductOutput;

@Singleton
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductElasticSearchService productElasticSearchService;

  public ProductServiceImpl(
      ProductRepository productRepository,
      ProductElasticSearchService productElasticSearchService) {
    this.productRepository = productRepository;
    this.productElasticSearchService = productElasticSearchService;
  }

  @Override
  public ProductOutput getById(Long id) {
    return productElasticSearchService.getById(id);
  }

  @Override
  public ProductListOutput paginate(Integer page, Integer size) {
    Pageable pageable = Pageable.from(page, size);
    Page<ProductOutput> paged = productRepository.findAll(pageable).map(ProductOutput::new);
    return new ProductListOutput(paged);
  }

  @Override
  public ProductOutput createProduct(CreateProductInput input) {
    Product product = productRepository.save(input.toEntity());
    productElasticSearchService.createProduct(product);
    return new ProductOutput(product);
  }

  @Override
  public ProductOutput updateProduct(Long id, UpdateProductInput input) {
    Optional<Product> optional = productRepository.findById(id);
    Product product = optional.orElseThrow(() -> new EntityNotFoundException("Product not found"));
    if (!States.isNullOrBlank(input.getName())) {
      product.setName(input.getName());
    }
    if (!States.isNullOrBlank(input.getDescription())) {
      product.setDescription(input.getDescription());
    }
    Product updatedProduct = productRepository.update(product);
    productElasticSearchService.updateProduct(product);
    return new ProductOutput(updatedProduct);
  }

  @Override
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
    productElasticSearchService.deleteProduct(id);
  }

  @Override
  public ProductListOutput search(Integer page, Integer size, String searchText) {
    return productElasticSearchService.search(page, size, searchText);
  }
}
