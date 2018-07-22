package com.danzal.lib_project.converters.Author;

import com.danzal.lib_project.commands.AuthorCommand;
import com.danzal.lib_project.converters.Book.BookCommandToBook;
import com.danzal.lib_project.domain.Author;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorCommandToAuthor implements Converter<AuthorCommand, Author> {


    private final BookCommandToBook bookConverter;

    public AuthorCommandToAuthor(BookCommandToBook bookConverter) {
        this.bookConverter = bookConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Author convert(AuthorCommand source) {

        if (source == null) {
            return null;
        }

        final Author author = new Author();

        author.setNationality(source.getNationality());
        author.setLastName(source.getLastName());
        author.setId(source.getId());
        author.setFirstName(source.getFirstName());
        author.setBookId(source.getBookId());

        if (source.getBookId() != null && source.getBookId().size() > 0) {
            source.getBookId().forEach(id -> author.getBookId().add(id));
        }

        if (source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks().forEach(book -> author.getBooks().add(bookConverter.convert(book)));
        }


        return author;
    }
}
