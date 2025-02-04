package com.jgmedellin.cards.service;

import com.jgmedellin.cards.dto.CardDto;

public interface ICardsService {

  /**
   * Creates a new card given a mobile number.
   * @param mobileNumber Mobile number of the customer
   */
  void createCard(String mobileNumber);

  /**
   * Fetches the card details given the card number.
   * @param cardNumber Card number
   * @return CardDto object with the card details
   */
  CardDto fetchCard(String cardNumber);

  /**
   * Updates the details based on new card details.
   * @param cardDto Card details (mobile number, card number, card type, total limit, amount used)
   * @return boolean value indicating the success of the update operation
   */
  boolean updateCard(CardDto cardDto);

  /**
   * Deletes the card given the mobile number.
   * @param mobileNumber Mobile number
   * @return boolean value indicating the success of the delete operation
   */
  boolean deleteCard(String mobileNumber);

}