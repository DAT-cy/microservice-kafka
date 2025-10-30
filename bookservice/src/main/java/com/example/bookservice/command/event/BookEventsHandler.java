package com.example.bookservice.command.event;

import com.example.bookservice.command.data.BookEntity;
import com.example.bookservice.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.bouncycastle.util.Exceptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void  on(BookCreateEvent bookCreateEvent)
    {
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookCreateEvent, bookEntity);
        bookRepository.save(bookEntity);
    }

    @EventHandler
    public void  on(BookUpdateEvent bookUpdateEvent)
    {
        try {
            Optional<BookEntity> bookEntity = bookRepository.findById(bookUpdateEvent.getBookId());
            bookEntity.ifPresent(bookEntity1 -> {
                BeanUtils.copyProperties(bookUpdateEvent, bookEntity1);
                bookRepository.save(bookEntity1);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @EventHandler
    public void  on(BookDeleteEvent bookDeleteEvent)
    {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookDeleteEvent.getBookId());
        bookEntity.ifPresent(bookEntity1 -> {
            bookRepository.delete(bookEntity1);
        });
    }
}
