package org.backend.completesecurity.utils;

public interface TokenBlacklist {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}
