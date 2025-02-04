package com.jgmedellin.cards.repository;

import com.jgmedellin.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

  /**
   * Fetches the card details from the DB given the mobile number.
   * @param mobileNumber Mobile number of the customer.
   * @return Optional object of the card details.
   */
  Optional<Cards> findByMobileNumber(String mobileNumber);

  /**
   * Fetches the card details from the DB given the card number.
   * @param cardNumber Card number of the customer.
   * @return Optional object of the card details.
   */
  Optional<Cards> findByCardNumber(String cardNumber);

}