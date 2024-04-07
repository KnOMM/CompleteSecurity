package org.backend.completesecurity.service;

import org.backend.completesecurity.entity.BlockedToken;
import org.backend.completesecurity.repository.BlockedTokenRepository;
import org.backend.completesecurity.utils.TokenBlacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryTokenBlacklist implements TokenBlacklist {
    @Autowired
    private BlockedTokenRepository blockedTokenRepository;

//    private final Set<String> blacklist = new HashSet<>();
    @Override
    public void addToBlacklist(String token) {
        blockedTokenRepository.save(new BlockedToken(token, UUID.randomUUID().toString()));
//        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        Iterable<BlockedToken> all = blockedTokenRepository.findAll();
        List<BlockedToken> list = new ArrayList<>();
        for (BlockedToken b : all)
            list.add(b);

        return list.stream()
                .map(BlockedToken::getJwt)
                .toList()
                .contains(token);
    }
}
