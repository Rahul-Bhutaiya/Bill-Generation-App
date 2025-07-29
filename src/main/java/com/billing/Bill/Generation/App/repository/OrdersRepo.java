package com.billing.Bill.Generation.App.repository;

import com.billing.Bill.Generation.App.DTO.Response.OrdersList;
import com.billing.Bill.Generation.App.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Integer> {
    @Query(
            value = """
                    select
                    	o.order_id,
                        o.customer_name,
                        o.contact_number,
                        o.product_id,
                        pl.name,
                        o.product_count,
                        pl.category
                    from orders o
                    join product_list pl
                    	on pl.product_id = o.product_id
                    	and date(o.created_at)=current_date();
                    """,
            nativeQuery = true
    )
    List<OrdersList> getTodayOrders();
}
