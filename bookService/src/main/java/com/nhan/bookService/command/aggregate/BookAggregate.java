package com.nhan.bookService.command.aggregate;

import com.nhan.bookService.command.command.CreateBookCommand;
import com.nhan.bookService.command.event.BookCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;



    public BookAggregate() {
    }

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(command,bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @EventSourcingHandler
    public void on (BookCreatedEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getReady();
        this.name = event.getName();
    }
}
