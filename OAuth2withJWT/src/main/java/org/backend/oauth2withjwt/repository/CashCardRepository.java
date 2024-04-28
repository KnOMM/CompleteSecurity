package org.backend.oauth2withjwt.repository;

import org.backend.oauth2withjwt.entity.CashCard;
import org.springframework.data.repository.CrudRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {
}
