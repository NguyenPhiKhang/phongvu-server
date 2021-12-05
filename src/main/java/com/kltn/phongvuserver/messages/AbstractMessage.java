package com.kltn.phongvuserver.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractMessage<T> {
    protected int statusCode;
    protected String statusText;
    protected String message;
    protected T value;
}
