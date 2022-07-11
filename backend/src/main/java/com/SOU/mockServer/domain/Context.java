package com.SOU.mockServer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Context <T>{
    public T data;
    public int length;

}
