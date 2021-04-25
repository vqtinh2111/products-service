package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.entity.Product;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.output.ProductListOutput;

@Singleton
public class SearchProductDataFetcher implements DataFetcher<ProductListOutput> {

  private final ProductService productService;

  public SearchProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public ProductListOutput get(DataFetchingEnvironment env) throws Exception {
    String searchText = env.getArgument("searchText");
    Integer page = env.getArgumentOrDefault("page", 0);
    Integer size = env.getArgumentOrDefault("size", 10);
    return productService.search(page, size, searchText);
  }
}
