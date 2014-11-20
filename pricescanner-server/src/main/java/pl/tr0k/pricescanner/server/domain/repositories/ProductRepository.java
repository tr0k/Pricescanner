package pl.tr0k.pricescanner.server.domain.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.tr0k.pricescanner.server.domain.Product;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Product findByEan(String ean);

    @Query("select p from Product p where concat(lower(p.name), ' ', lower(p.ean), ' ', p.price, ' ', lower(p.description)) like lower(concat('%', :filter, '%'))")
    List<Product> findByAnyField(@Param("filter") String filter, Pageable pageable);
}
