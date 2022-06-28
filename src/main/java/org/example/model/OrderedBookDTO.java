package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.OrderedBook;

import java.util.UUID;

@Getter
@Setter
public class OrderedBookDTO {
    private int id;
    private BookDTO book;

    public OrderedBookDTO(BookDTO book) {
        this.book = book;
    }

    public static OrderedBookDTO fromEntity(OrderedBook orderedBook) {
        return new OrderedBookDTO(BookDTO.fromEntity(orderedBook.getBook()));
    }

    public static int getBookId(OrderedBookDTO orderedBookDTO) {
        return orderedBookDTO.getBook().getId();
    }

}
