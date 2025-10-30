package com.example.bookservice.query.controller;

import com.example.bookservice.query.model.BookResponseModel;
import com.example.bookservice.query.queries.GetAllBookQuery;
import com.example.bookservice.query.queries.GetBookByIdQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookQueryController {
    private final QueryGateway  queryGateway;

    @GetMapping
    public ResponseEntity<List<BookResponseModel>> getAllBooks(){
        GetAllBookQuery  getAllBookQuery = new GetAllBookQuery();
        CompletableFuture<List<BookResponseModel>> bookResponseModelCompletableFuture = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class));
        return  ResponseEntity.ok(bookResponseModelCompletableFuture.join());
    }

    @GetMapping("/{bookIds}")
    public ResponseEntity<BookResponseModel> getAllBooksByIds(@PathVariable String bookIds){
        GetBookByIdQuery getAllBookQuery = new GetBookByIdQuery(bookIds);
        CompletableFuture<BookResponseModel> getDetailBooks = queryGateway.query(getAllBookQuery, BookResponseModel.class);
        return ResponseEntity.ok(getDetailBooks.join());

    }
}
