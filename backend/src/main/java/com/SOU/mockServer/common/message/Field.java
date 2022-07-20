package com.SOU.mockServer.common.message;

import lombok.Getter;

public final class Field<T> {
    @Getter
    final int length;

    // swagger에서 각 Field의 value값 확인을 위해 추가
    @Getter
    T value;

    public Field(int length, T value) {
        this.length = length;
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}