package com.decodinator.liroth.core.interfaces;

public interface PottableTerrain {
    default boolean canBePotted() {
        return true;
    }
}