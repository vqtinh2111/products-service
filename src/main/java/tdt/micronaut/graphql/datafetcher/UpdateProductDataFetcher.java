package tdt.micronaut.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.input.UpdateProductInput;

@Singleton
public class UpdateProductDataFetcher implements DataFetcher<Boolean> {

  private static final Logger logger = LoggerFactory.getLogger(UpdateProductDataFetcher.class);

  private final ProductService productService;

  public UpdateProductDataFetcher(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Boolean get(DataFetchingEnvironment env) throws Exception {
    logger.debug("updateProduct, args={}", env.getArguments());
    try {
      Long id = Long.valueOf(env.getArgument("id"));
      String name = env.getArgument("name");
      String description = env.getArgument("description");
      UpdateProductInput input = new UpdateProductInput();
      input.setName(name);
      input.setDescription(description);
      productService.updateProduct(id, input);
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("error while update product args={}, cause", env.getArguments(),  e);
      return Boolean.FALSE;
    }
  }
}
