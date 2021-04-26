package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdt.micronaut.service.ProductService;

@Singleton
public class DeleteProductDataFetcher implements DataFetcher<Boolean> {

  private static final Logger logger = LoggerFactory.getLogger(DeleteProductDataFetcher.class);

  private final ProductService productService;

  public DeleteProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Boolean get(DataFetchingEnvironment env) throws Exception {
    try {
      logger.debug("deleteProduct, args={}", env.getArguments());
      Long id = Long.valueOf(env.getArgument("id"));
      productService.deleteProduct(id);
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("error while delete product, args={}, cause", env.getArguments(), e);
      return Boolean.FALSE;
    }
  }
}
