package org.backend.completesecurity.repository;

import org.backend.completesecurity.entity.BlockedToken;
import org.springframework.data.repository.CrudRepository;

public interface BlockedTokenRepository extends CrudRepository<BlockedToken, String> {
}
