package com.eazybytes.cards.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;

@Service
public class CardsServiceImpl implements ICardsService {
	
	@Autowired
	private CardsRepository cardsRepository;
	
	/**
     * @param mobileNumber - Mobile Number of the Customer
     */

	@Override
	public void createCard(String mobileNumber) {
		Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
		if(optionalCards.isPresent()) {
			throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
		}
		cardsRepository.save(createNewCard(mobileNumber));
	}
	
	/**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
	
	private Cards createNewCard(String mobileNumber) {
		Cards newCards = new Cards();
		long randomNumber = 100000000000L + new Random().nextInt(900000000);
		newCards.setCardNumber(Long.toString(randomNumber));
		newCards.setMobileNumber(mobileNumber);
		newCards.setCardType(CardsConstants.CREDIT_CARD);
		newCards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
		newCards.setAvailableAmount(0);
		newCards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
		//newCards.setCreatedBy("Anonymous");
		//newCards.setCreatedAt(LocalDateTime.now());
		
		return newCards;
	}
	
	/**
    *
    * @param mobileNumber - Input mobile Number
    * @return Card Details based on a given mobileNumber
    */

	@Override
	public CardsDto fetchCard(String mobileNumber) {
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
				);
		return CardsMapper.mapToCardsDto(cards, new CardsDto());
	}

	/**
    *
    * @param cardsDto - CardsDto Object
    * @return boolean indicating if the update of card details is successful or not
    */
	
	@Override
	public boolean updateCard(CardsDto cardsDto) {
		Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
	}

	/**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
	
	@Override
	public boolean deleteCard(String mobileNumber) {
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
	}

}
