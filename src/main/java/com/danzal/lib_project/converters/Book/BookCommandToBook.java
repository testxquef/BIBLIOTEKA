package com.danzal.lib_project.converters.Book;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.domain.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {


    @Nullable
    @Synchronized
    @Override
    public Book convert(BookCommand source) {

        if (source == null) {
            return null;
        }

        final Book book = new Book();

        book.setTitle(source.getTitle());
        book.setPubYear(source.getPubYear());
        book.setPublisher(source.getPublisher());
        book.setLibrarian(source.getLibrarian());
        book.setLanguage(source.getLanguage());
        book.setId(source.getId());
        book.setFormat(source.getFormat());
        book.setDescription(source.getDescription());
        book.setCategory(source.getCategory());

        if (source.getAuthorId() != null && source.getAuthorId().size() > 0) {
            source.getAuthorId().forEach(id -> book.getAuthorId().add(id));
        }


        return book;
    }


}
