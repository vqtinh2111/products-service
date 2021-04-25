package tdt.micronaut.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import tdt.micronaut.graphql.datafetcher.CreateProductDataFetcher;
import tdt.micronaut.graphql.datafetcher.DeleteProductDataFetcher;
import tdt.micronaut.graphql.datafetcher.GetProductByIdDataFetcher;
import tdt.micronaut.graphql.datafetcher.PaginateProductDataFetcher;
import tdt.micronaut.graphql.datafetcher.SearchProductDataFetcher;
import tdt.micronaut.graphql.datafetcher.UpdateProductDataFetcher;

@Factory
public class GraphQLFactory {


  @Bean
  @Singleton
  public GraphQL graphQL(
      ResourceResolver resourceResolver,
      PaginateProductDataFetcher paginateProductDataFetcher,
      CreateProductDataFetcher createProductDataFetcher,
      UpdateProductDataFetcher updateProductDataFetcher,
      DeleteProductDataFetcher deleteProductDataFetcher,
      GetProductByIdDataFetcher getProductByIdDataFetcher,
      SearchProductDataFetcher searchProductDataFetcher) {
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    TypeDefinitionRegistry typeRegistry = buildRegistry(resourceResolver);
    RuntimeWiring runtimeWiring =
        RuntimeWiring.newRuntimeWiring()
            .type(
                "Query",
                typeWiring ->
                    typeWiring
                        .dataFetcher("getProductById", getProductByIdDataFetcher)
                        .dataFetcher("searchProduct", searchProductDataFetcher)
                        .dataFetcher("paginateProduct", paginateProductDataFetcher))
            .type(
                "Mutation",
                typeWiring ->
                    typeWiring
                        .dataFetcher("createProduct", createProductDataFetcher)
                        .dataFetcher("updateProduct", updateProductDataFetcher)
                        .dataFetcher("deleteProduct", deleteProductDataFetcher))
            .build();
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    return GraphQL.newGraphQL(graphQLSchema).build();
  }

  @NotNull
  private TypeDefinitionRegistry buildRegistry(ResourceResolver resourceResolver) {
    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
    typeRegistry.merge(
        schemaParser.parse(
            new BufferedReader(
                new InputStreamReader(
                    resourceResolver.getResourceAsStream("classpath:schema.graphqls").get()))));
    return typeRegistry;
  }
}
