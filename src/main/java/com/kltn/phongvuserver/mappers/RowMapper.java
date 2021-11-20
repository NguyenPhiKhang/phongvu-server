package com.kltn.phongvuserver.mappers;


public interface RowMapper<T, S> {
    T mapRow(S s);
//    T mapRow(S s, IImageDataService repository);
}