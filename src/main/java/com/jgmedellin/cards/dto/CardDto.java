package com.jgmedellin.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "Cards", description = "Schema to hold Card information")
public class CardDto {

  @NotEmpty(message = "Mobile Number can not be a null or empty")
  @Pattern(regexp="^[0-9]{10}$",message = "Mobile Number must be 10 digits")
  @Schema(description = "Mobile Number of Customer", example = "4354437687")
  private String mobileNumber;

  @NotEmpty(message = "Card Number can not be a null or empty")
  @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits")
  @Schema(description = "Card Number of the customer", example = "100646930341")
  private String cardNumber;

  @NotEmpty(message = "Card Type can not be a null or empty")
  @Schema(description = "Card type", example = "Credit")
  @NotEmpty(message = "Card type can not be null or empty")
  private String cardType;

  @Schema(description = "Total limit", example = "250000")
  @Positive(message = "Total limit should be greater than zero")
  private int totalLimit;

  @Schema(description = "Amount used", example = "50000")
  @PositiveOrZero(message = "Amount used should be equal or greater than zero")
  private int amountUsed;

  @Schema(description = "Available amount", example = "200000")
  @PositiveOrZero(message = "Available amount should be equal or greater than zero")
  private int availableAmount;

}