package tdt.micronaut.graphql.datafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdt.micronaut.common.States;
import tdt.micronaut.service.ProductService;
import tdt.micronaut.service.input.CreateProductInput;
import tdt.micronaut.service.output.ProductOutput;

@Singleton
public class CreateProductDataFetcher implements DataFetcher<ProductOutput> {

  private static final Logger logger = LoggerFactory.getLogger(CreateProductDataFetcher.class);

  private final ObjectMapper objectMapper;
  private final ProductService productService;

  public CreateProductDataFetcher(ObjectMapper objectMapper,
      ProductService productService) {
    this.objectMapper = objectMapper;
    this.productService = productService;
  }

  @Override
  public ProductOutput get(DataFetchingEnvironment env) throws Exception {
    logger.debug("createProduct, args={}", env.getArguments());
    CreateProductInput input = objectMapper.convertValue(env.getArguments(), CreateProductInput.class);
    if (States.isNullOrBlank(input.getName())) {
      throw new RuntimeException("Product name must not be null or blank");
    }
    return productService.createProduct(input);
  }
}
