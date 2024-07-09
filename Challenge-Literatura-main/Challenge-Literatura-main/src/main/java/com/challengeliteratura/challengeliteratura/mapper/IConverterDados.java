package com.challengeliteratura.challengeliteratura.mapper;

public interface IConverterDados {

    <T> T obterDados(String json, Class<T> classe);

}