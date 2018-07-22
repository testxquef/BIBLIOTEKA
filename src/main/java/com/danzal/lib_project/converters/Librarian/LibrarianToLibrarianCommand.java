package com.danzal.lib_project.converters.Librarian;

import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.domain.Book;
import com.danzal.lib_project.domain.Librarian;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LibrarianToLibrarianCommand implements Converter<Librarian, LibrarianCommand> {

    @Nullable
    @Synchronized
    @Override
    public LibrarianCommand convert(Librarian source) {
        if (source == null) {
            return null;
        }

        final LibrarianCommand librarianCommand = new LibrarianCommand();

        librarianCommand.setAdress(source.getAdress());
        librarianCommand.setLibrarianNumber(source.getLibrarianNumber());
        librarianCommand.setLastName(source.getLastName());
        librarianCommand.setId(source.getId());
        librarianCommand.setFirstName(source.getFirstName());

        if (source.getLoanBooks() != null && source.getLoanBooks().size() > 0) {
            source.getLoanBooks().forEach((Book book) -> librarianCommand.getLoanBooks().add(book));
        }

        return librarianCommand;
    }
}
