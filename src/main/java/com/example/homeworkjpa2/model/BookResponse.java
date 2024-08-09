package com.example.homeworkjpa2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookResponse<T> {
    private String message;
    private HttpStatus httpStatus;
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private T payload;
    private Timestamp timestamp;
}
