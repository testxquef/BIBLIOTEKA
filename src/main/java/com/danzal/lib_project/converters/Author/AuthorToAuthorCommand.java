package com.danzal.lib_project.converters.Author;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.converters.Book.BookToBookCommand;
import com.danzal.lib_project.domain.Author;
import com.danzal.lib_project.domain.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorToAuthorCommand implements Converter<Author, AuthorCommand> {

    private final BookToBookCommand bookConverter;

    public AuthorToAuthorCommand(BookToBookCommand bookConverter) {
        this.bookConverter = bookConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public AuthorCommand convert(Author source) {

        if (source == null) {
            return null;
        }

        final AuthorCommand authorCommand = new AuthorCommand();

        authorCommand.setNationality(source.getNationality());
        authorCommand.setLastName(source.getLastName());
        authorCommand.setId(source.getId());
        authorCommand.setFirstName(source.getFirstName());

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks().forEach((Book book) -> authorCommand.getBooks().add(bookConverter.convert(book)));
        }


        return authorCommand;
    }
}
