package org.backend.oauth2withjwt.contoller;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.entity.CashCard;
import org.backend.oauth2withjwt.repository.CashCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
@RequiredArgsConstructor
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        return cashCardOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping // UriComponentsBuilder is automatically injected by IoC
//    @Transactional
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {

        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}") // can be anything instead of id, but blank
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
