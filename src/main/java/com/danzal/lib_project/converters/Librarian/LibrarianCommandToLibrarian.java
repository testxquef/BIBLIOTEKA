package com.danzal.lib_project.converters.Librarian;


import com.danzal.lib_project.commands.LibrarianCommand;
import com.danzal.lib_project.domain.Librarian;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LibrarianCommandToLibrarian implements Converter<LibrarianCommand, Librarian> {

    @Nullable
    @Synchronized
    @Override
    public Librarian convert(LibrarianCommand source) {
        if (source == null) {
            return null;
        }

        final Librarian librarian = new Librarian();

        librarian.setAdress(source.getAdress());
        librarian.setLibrarianNumber(source.getLibrarianNumber());
        librarian.setLastName(source.getLastName());
        librarian.setId(source.getId());
        librarian.setFirstName(source.getFirstName());

        if (source.getLoanBookId() != null && source.getLoanBookId().size() > 0) {
            source.getLoanBookId().forEach(id -> librarian.getLoanBookId().add(id));
        }

        return librarian;
    }
}
