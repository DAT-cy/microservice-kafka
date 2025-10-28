package com.example.bookservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.checkerframework.checker.units.qual.N;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookCommand {

    @TargetAggregateIdentifier
    private String bookId;
    private String name;
    private String author;
}
