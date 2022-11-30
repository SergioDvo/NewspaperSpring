package dao.impl;

import io.vavr.control.Either;
import model.Reader;

import java.util.List;

public interface DaoReadersImpl {
    Either<String, List<Reader>> getAll();

    Either<String, Boolean> saveReaderList(List<Reader> readerList);
}
