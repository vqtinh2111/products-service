package tdt.micronaut.service.output;

import io.micronaut.data.model.Page;
import java.util.List;

public class ProductListOutput {

  private List<ProductOutput> records;
  private Long total;
  private Integer size;
  private Integer page;


  public ProductListOutput(Long total, Integer page, List<ProductOutput> outputs) {
    this.total = total;
    this.page = page;
    this.size = outputs.size();
    this.records = outputs;
  }

  public ProductListOutput(Page<ProductOutput> page) {
    this.total = page.getTotalSize();
    this.page = page.getPageNumber();
    this.records = page.getContent();
    this.size = records.size();
  }

  public List<ProductOutput> getRecords() {
    return records;
  }

  public ProductListOutput setRecords(List<ProductOutput> records) {
    this.records = records;
    return this;
  }

  public Long getTotal() {
    return total;
  }

  public ProductListOutput setTotal(Long total) {
    this.total = total;
    return this;
  }

  public Integer getSize() {
    return size;
  }

  public ProductListOutput setSize(Integer size) {
    this.size = size;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public ProductListOutput setPage(Integer page) {
    this.page = page;
    return this;
  }
}
