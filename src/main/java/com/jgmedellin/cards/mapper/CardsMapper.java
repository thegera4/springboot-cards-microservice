package com.jgmedellin.cards.mapper;

import com.jgmedellin.cards.dto.CardDto;
import com.jgmedellin.cards.entity.Cards;

public class CardsMapper {

  public static CardDto mapToCardDto(Cards card, CardDto cardDto) {
    cardDto.setCardNumber(card.getCardNumber());
    cardDto.setCardType(card.getCardType());
    cardDto.setMobileNumber(card.getMobileNumber());
    cardDto.setTotalLimit(card.getTotalLimit());
    cardDto.setAvailableAmount(card.getAvailableAmount());
    cardDto.setAmountUsed(card.getAmountUsed());
    return cardDto;
  }

  public static Cards mapToCard(CardDto cardDto, Cards card) {
    card.setCardNumber(cardDto.getCardNumber());
    card.setCardType(cardDto.getCardType());
    card.setMobileNumber(cardDto.getMobileNumber());
    card.setTotalLimit(cardDto.getTotalLimit());
    card.setAvailableAmount(cardDto.getAvailableAmount());
    card.setAmountUsed(cardDto.getAmountUsed());
    return card;
  }
}