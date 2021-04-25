package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.output.ProductOutput;

@Singleton
public class GetProductByIdDataFetcher implements DataFetcher<ProductOutput> {

  private final ProductService productService;

  public GetProductByIdDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public ProductOutput get(DataFetchingEnvironment env) throws Exception {
    Long id = Long.valueOf(env.getArgument("id"));
    return productService.getById(id);
  }
}
