package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCardRepository
        extends JpaRepository<CustomerCard, Customer>,
        JpaSpecificationExecutor<CustomerCard> {
    List<CustomerCard> findByCustomerCardIds_Customer_Email(String email);

}

