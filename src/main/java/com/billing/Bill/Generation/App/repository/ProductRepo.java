package com.billing.Bill.Generation.App.repository;

import com.billing.Bill.Generation.App.DTO.Response.StockList;
import com.billing.Bill.Generation.App.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query(
            value = """
                    select
                    	product_id,
                        name,
                        price,
                        stock_quantity,
                        category
                    from product_list
                    limit :offset, :limit
                    ;
                    """,
            nativeQuery = true
    )
    List<StockList> getProductList(@Param("offset") int offset,@Param("limit") int limit);

    @Query(
            value = """
                    select
                    	product_id,
                        name,
                        price,
                        stock_quantity,
                        category
                    from product_list
                    order by stock_quantity
                    ;
                    """,
            nativeQuery = true
    )
    List<StockList> getAllProductStock();
}
