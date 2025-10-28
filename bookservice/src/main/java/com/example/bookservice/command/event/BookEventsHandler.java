package com.example.bookservice.command.event;

import com.example.bookservice.command.data.BookEntity;
import com.example.bookservice.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.bouncycastle.util.Exceptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
            BookEntity bookEntity = bookRepository.findById(bookUpdateEvent.getBookId()).orElse(null);
            if (bookEntity == null) {
                throw new RuntimeException();
            }
            BeanUtils.copyProperties(bookUpdateEvent, bookEntity);
            bookRepository.save(bookEntity);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @EventHandler
    public void  on(BookDeleteEvent bookDeleteEvent)
    {
        BookEntity bookEntity = bookRepository.findById(bookDeleteEvent.getBookId()).orElse(null);
        if (bookEntity == null) {
            throw new RuntimeException();
        }
        bookRepository.delete(bookEntity);
    }
}
