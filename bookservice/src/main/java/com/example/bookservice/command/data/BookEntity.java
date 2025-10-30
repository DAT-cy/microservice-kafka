package com.example.bookservice.command.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {
    @Id
    @Column(name = "book_id")
    private String bookId;

    private String name;
    private String author;

    @Column(name = "is_ready")
    private boolean isReady;



}
