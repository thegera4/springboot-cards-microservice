package com.jgmedellin.cards.service.impl;

import com.jgmedellin.cards.constants.CardsConstants;
import com.jgmedellin.cards.dto.CardDto;
import com.jgmedellin.cards.entity.Cards;
import com.jgmedellin.cards.exception.CardAlreadyExistsException;
import com.jgmedellin.cards.exception.ResourceNotFoundException;
import com.jgmedellin.cards.mapper.CardsMapper;
import com.jgmedellin.cards.repository.CardsRepository;
import com.jgmedellin.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

  private CardsRepository cardsRepository;

  @Override
  public void createCard(String mobileNumber) {
    // Validate if the card already exists
    Optional<Cards> optionalCard = cardsRepository.findByMobileNumber(mobileNumber);
    if (optionalCard.isPresent()) { throw new CardAlreadyExistsException("Card number already exists!"); }

    // Create and save in the DB a new card for the customer with the utility method
    cardsRepository.save(createNewCard(mobileNumber));
  }

  /**
   * Utility method that creates a new Card.
   * @return the new card details
   */
  private Cards createNewCard(String mobileNumber) {
    Cards newCard = new Cards();
    long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType(CardsConstants.CREDIT);
    newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
    return newCard;
  }

  @Override
  public CardDto fetchCard(String mobileNumber) {
    // Fetch the card details by mobile number or throw an exception if not found
    Cards card = cardsRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

    // Map the card details to the CardDto object and return it
    return CardsMapper.mapToCardDto(card, new CardDto());
  }

  @Override
  public boolean updateCard(CardDto cardDto) {
    // Fetch the card details by card number or throw an exception if not found
    Cards card = cardsRepository.findByCardNumber(cardDto.getCardNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardDto.getCardNumber()));

    // Map the card details to the Card object
    CardsMapper.mapToCard(cardDto, card);

    // Save the updated card details in the DB and return true
    cardsRepository.save(card);
    return true;
  }

  @Override
  public boolean deleteCard(String mobileNumber) {
    // Fetch the card details by mobile number or throw an exception if not found
    Cards card = cardsRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

    // Delete the card details from the DB and return true
    cardsRepository.deleteById(card.getCardId());
    return true;
  }

}