package org.backend.completesecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash
@AllArgsConstructor
@Getter
public class BlockedToken implements Serializable {
    private String jwt;
    @Id
    private String id;
}
