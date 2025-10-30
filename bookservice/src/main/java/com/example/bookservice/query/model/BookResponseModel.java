package com.example.bookservice.query.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseModel {
    private String bookId;
    private String name;
    private String author;
    private boolean isRead;
}
