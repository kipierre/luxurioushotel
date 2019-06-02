package org.cephas.hotel.repository;

import org.cephas.hotel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 31-05-19.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
