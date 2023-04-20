package com.course.kafka.repository;

import com.course.kafka.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Section 11: 52. Order App - Database
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
}
