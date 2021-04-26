package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.output.ProductListOutput;

@Singleton
public class PaginateProductDataFetcher implements DataFetcher<ProductListOutput> {

  private static final Logger logger = LoggerFactory.getLogger(PaginateProductDataFetcher.class);

  private final ProductService productService;

  public PaginateProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public ProductListOutput get(DataFetchingEnvironment env) {
    logger.info("paginate products");
    Integer page = env.getArgumentOrDefault("page", 0);
    Integer size = env.getArgumentOrDefault("size", 10);
    return productService.paginate(page, size);
  }
}
