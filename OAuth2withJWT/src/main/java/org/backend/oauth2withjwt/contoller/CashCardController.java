package org.backend.oauth2withjwt.contoller;

import lombok.RequiredArgsConstructor;
import org.backend.oauth2withjwt.entity.CashCard;
import org.backend.oauth2withjwt.repository.CashCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
@RequiredArgsConstructor
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository.findByIdAndOwner(requestedId, principal.getName()));
        return cashCardOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping // UriComponentsBuilder is automatically injected by IoC
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {

        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.getAmount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}") // can be anything instead of id, but blank
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

//    @GetMapping()
//    private ResponseEntity<Iterable<CashCard>> findAll() {
//        return ResponseEntity.ok(cashCardRepository.findAll());
//    }

    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
//                        pageable.getSort()  // default for page number - 0, page size - 20
                        pageable.getSortOr(Sort.by
                                (Sort.Direction.ASC,
                                        "amount")
                        )));
        return ResponseEntity.ok(page.getContent());
    }
}
