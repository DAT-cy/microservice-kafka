package com.example.bookservice.command.controller;

import com.example.bookservice.command.command.CreateBookCommand;
import com.example.bookservice.command.command.DeleteBookCommand;
import com.example.bookservice.command.command.UpdateBookCommand;
import com.example.bookservice.command.model.BookRequestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookCommandController
{
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook( @RequestBody BookRequestModel model){
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(),model.getName(),model.getAuthor(),true);
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{bookIds}")
    public String updateBook( @PathVariable String bookIds ,@RequestBody BookRequestModel model){
        UpdateBookCommand command = new UpdateBookCommand(bookIds,model.getName(),model.getAuthor());
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{bookIds}")
    public String deleteBook( @PathVariable String bookIds){
        DeleteBookCommand command = new DeleteBookCommand(bookIds);
        return commandGateway.sendAndWait(command);
    }
    
}
