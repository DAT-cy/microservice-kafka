package com.example.bookservice.query.projection;

import com.example.bookservice.command.data.BookEntity;
import com.example.bookservice.command.data.BookRepository;
import com.example.bookservice.command.model.BookRequestModel;
import com.example.bookservice.query.model.BookResponseModel;
import com.example.bookservice.query.queries.GetAllBookQuery;
import com.example.bookservice.query.queries.GetBookByIdQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    private List<BookResponseModel> handle(GetAllBookQuery query) {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream()
                .map(this::toModel)
                .toList();
    }

    private BookResponseModel toModel(BookEntity bookEntity) {
        return BookResponseModel.builder()
                .bookId(bookEntity.getBookId())
                .author(bookEntity.getAuthor())
                .name(bookEntity.getName())
                .isRead(bookEntity.isReady())
                .build();
    }

    @QueryHandler
    private BookResponseModel getDetailBook(GetBookByIdQuery query) {
        Optional<BookEntity> bookEntities = bookRepository.findById(query.getBookId());
        return bookEntities.map(this::toModel).orElseThrow(() -> new RuntimeException("Book Not Found"));
    }
}
