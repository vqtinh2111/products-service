package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import tdt.micronaut.service.ProductService;

@Singleton
public class DeleteProductDataFetcher implements DataFetcher<Boolean> {


  private final ProductService productService;

  public DeleteProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Boolean get(DataFetchingEnvironment env) throws Exception {
    try {
      Long id = Long.valueOf(env.getArgument("id"));
      productService.deleteProduct(id);
      return Boolean.TRUE;
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }
}
