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
    List<CustomerCard> findByCustomer_Id(long id);


//    @Transactional
//    @Modifying
//    @Query("update Card c set c.activities = ?1")
//    Specification<CustomerCard> getCustomerCards(String customerName) {
//        return (root, query, criteriaBuilder) -> {
//            Join<CustomerCard, Customer> customerCards = root.join("cards");
//            return criteriaBuilder.equal(customerCards.get("customer").get("name"), customerName);
//        };
//    }
}

