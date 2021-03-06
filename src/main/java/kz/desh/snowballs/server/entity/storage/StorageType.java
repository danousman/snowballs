package kz.desh.snowballs.server.entity.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StorageType {
    BASIC(100),
    EXTENDED(200);

    private final int size;
}