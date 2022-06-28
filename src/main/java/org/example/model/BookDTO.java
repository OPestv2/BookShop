package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.Book;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookDTO {

    private int id;
    private String title;
    private int price;

    public static BookDTO fromEntity(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getPrice());
    }
}
