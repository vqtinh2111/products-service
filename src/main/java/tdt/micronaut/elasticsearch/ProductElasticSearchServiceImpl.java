package tdt.micronaut.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import tdt.micronaut.entity.Product;
import tdt.micronaut.service.output.ProductListOutput;
import tdt.micronaut.service.output.ProductOutput;

@Singleton
public class ProductElasticSearchServiceImpl implements ProductElasticSearchService {

  private static final String PRODUCT_INDEX = "products";

  private final RestHighLevelClient client;
  private final ObjectMapper objectMapper;

  public ProductElasticSearchServiceImpl(RestHighLevelClient client, ObjectMapper objectMapper) {
    this.client = client;
    this.objectMapper = objectMapper;
  }

  @Override
  @Transactional
  public void createProduct(Product product) {
    Map<String, Object> jsonMap = objectMapper.convertValue(product, Map.class);
    IndexRequest request = new IndexRequest(PRODUCT_INDEX);
    request.id(product.getId().toString()).source(jsonMap);
    try {
      client.index(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ProductOutput getById(Long productId) {
    GetRequest getRequest = new GetRequest(PRODUCT_INDEX, productId.toString());
    try {
      GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
      Product product = objectMapper.convertValue(response.getSource(), Product.class);
      return new ProductOutput(product);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Transactional
  public void updateProduct(Product product) {
    Map map = objectMapper.convertValue(product, Map.class);
    UpdateRequest updateRequest = new UpdateRequest();
    updateRequest.index(PRODUCT_INDEX);
    updateRequest.id(product.getId().toString());
    updateRequest.doc(map);
    try {
      client.update(updateRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  @Transactional
  public void deleteProduct(Long productId) {
    DeleteRequest request = new DeleteRequest();
    request.index(PRODUCT_INDEX);
    request.id(productId.toString());
    try {
      client.delete(request, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ProductListOutput search(Integer page, Integer size, String searchText) {
    SearchRequest request = new SearchRequest(PRODUCT_INDEX);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.from(page * size);
    searchSourceBuilder.size(size);
    searchSourceBuilder.query(QueryBuilders.simpleQueryStringQuery(searchText));
    request.source(searchSourceBuilder);
    try {
      SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
      SearchHits hits = searchResponse.getHits();
      SearchHit[] searchHits = hits.getHits();
      List<ProductOutput> outputs =
          Arrays.stream(searchHits)
              .filter(Objects::nonNull)
              .map(e -> objectMapper.convertValue(e.getSourceAsMap(), Product.class))
              .map(ProductOutput::new)
              .collect(Collectors.toList());
      long total = hits.getTotalHits().value;
      return new ProductListOutput(total, page, outputs);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}
