package com.danzal.lib_project.converters.Book;

import com.danzal.lib_project.commands.BookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {

    @Synchronized
    @Nullable
    @Override
    public BookCommand convert(Book source) {

        if (source == null) {
            return null;
        }

        final BookCommand bookCommand = new BookCommand();

        bookCommand.setTitle(source.getTitle());
        bookCommand.setPubYear(source.getPubYear());
        bookCommand.setPublisher(source.getPublisher());
        bookCommand.setLibrarian(source.getLibrarian());
        bookCommand.setLanguage(source.getLanguage());
        bookCommand.setId(source.getId());
        bookCommand.setFormat(source.getFormat());
        bookCommand.setDescription(source.getDescription());
        bookCommand.setCategory(source.getCategory());

        if (source.getAuthors() != null && source.getAuthors().size() > 0) {
            source.getAuthors().forEach((Author author) -> bookCommand.getAuthors().add(author));
        }


        return bookCommand;
    }
}
