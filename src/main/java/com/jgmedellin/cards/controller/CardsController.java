package com.jgmedellin.cards.controller;

import com.jgmedellin.cards.constants.CardsConstants;
import com.jgmedellin.cards.dto.CardDto;
import com.jgmedellin.cards.dto.CardsContactInfoDto;
import com.jgmedellin.cards.dto.ErrorResponseDto;
import com.jgmedellin.cards.dto.ResponseDto;
import com.jgmedellin.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cards API", description = "This API allows to Create, Read, Update and Delete cards in EazyBank")
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated // Perform validation on the request body
public class CardsController {

  private final ICardsService iCardsService;

  @Autowired
  public CardsController(ICardsService iCardsService) {
    this.iCardsService = iCardsService;
  }

  @Value("${build.version}")   // Injecting an env variable from application.properties (Approach 1)
  private String buildVersion;

  @Autowired
  private Environment environment; // Injecting the environment object to get the active profile (Approach 2)

  @Autowired
  private CardsContactInfoDto cardsContactInfoDto; // Injecting the CardsContactInfoDto bean (Approach 3)

  @Operation(summary = "Create a new card", description = "Endpoint to create a new card in EazyBank")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "Card created successfully"),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestParam @Pattern(regexp="(^$|[0-9]{10})",
          message = "Mobile number must be 10 digits") String mobileNumber) {
    iCardsService.createCard(mobileNumber);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
  }

  @Operation(summary = "Fetch Card Details", description = "Endpoint to fetch card details based on a mobile number")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @GetMapping("/fetch")
  public ResponseEntity<CardDto> fetchCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",
          message = "Mobile number must be 10 digits") String mobileNumber) {
    CardDto cardsDto = iCardsService.fetchCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
  }

  @Operation(summary = "Update Card Details", description = "Endpoint to update card details based on a card number")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = CardsConstants.MESSAGE_200),
          @ApiResponse(responseCode = "417", description = CardsConstants.MESSAGE_417_UPDATE),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardDto) {
    boolean isUpdated = iCardsService.updateCard(cardDto);
    if(isUpdated) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }else{
      return ResponseEntity
              .status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(summary = "Delete Card", description = "Endpoint to delete a card")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = CardsConstants.MESSAGE_200),
          @ApiResponse(responseCode = "417", description = CardsConstants.MESSAGE_417_DELETE),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",
          message = "Mobile number must be 10 digits") String mobileNumber) {
    boolean isDeleted = iCardsService.deleteCard(mobileNumber);
    if(isDeleted) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }else{
      return ResponseEntity
              .status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(summary = "Get build information", description = "Endpoint to check the current API version")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body("Current Build Version: " + buildVersion);
  }

  @Operation(summary = "Get java version", description = "Endpoint to check the Java version installed.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(summary = "Get contact information", description = "Endpoint to check the contact information.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(
                  responseCode = "500",
                  description = CardsConstants.MESSAGE_500,
                  content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
          )
  })
  @GetMapping("/contact-info")
  public ResponseEntity<CardsContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
  }

}