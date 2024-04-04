package org.backend.completesecurity.service;

import org.backend.completesecurity.utils.TokenBlacklist;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InMemoryTokenBlacklist implements TokenBlacklist {
    private final Set<String> blacklist = new HashSet<>();
    @Override
    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
