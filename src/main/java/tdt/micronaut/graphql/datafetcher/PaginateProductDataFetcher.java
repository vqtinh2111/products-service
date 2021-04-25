package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.output.ProductListOutput;

@Singleton
public class PaginateProductDataFetcher implements DataFetcher<ProductListOutput> {

  private final ProductService productService;

  public PaginateProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public ProductListOutput get(DataFetchingEnvironment env) {
    Integer page = env.getArgumentOrDefault("page", 0);
    Integer size = env.getArgumentOrDefault("size", 10);
    return productService.paginate(page, size);
  }
}
