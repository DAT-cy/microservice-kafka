package com.example.bookservice.command.aggregate;

import com.example.bookservice.command.command.CreateBookCommand;
import com.example.bookservice.command.command.DeleteBookCommand;
import com.example.bookservice.command.command.UpdateBookCommand;
import com.example.bookservice.command.event.BookCreateEvent;
import com.example.bookservice.command.event.BookDeleteEvent;
import com.example.bookservice.command.event.BookUpdateEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Setter
@Getter
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(command,bookCreateEvent);
        AggregateLifecycle.apply(bookCreateEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command){
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(command,bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }
    @CommandHandler
    public void handle(DeleteBookCommand command){
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(command,bookDeleteEvent);
        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent bookCreateEvent) {
        this.bookId = bookCreateEvent.getBookId();
        this.name = bookCreateEvent.getName();
        this.author = bookCreateEvent.getAuthor();
        this.isReady = bookCreateEvent.isReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent bookUpdateEvent) {
        this.bookId = bookUpdateEvent.getBookId();
        this.name = bookUpdateEvent.getName();
        this.author = bookUpdateEvent.getAuthor();
        this.isReady = bookUpdateEvent.isReady();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent bookDeleteEvent) {
        this.bookId = bookDeleteEvent.getBookId();
    }

}
