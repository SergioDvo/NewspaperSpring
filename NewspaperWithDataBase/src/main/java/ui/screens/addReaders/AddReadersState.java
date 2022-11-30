package ui.screens.addReaders;

import lombok.Data;
import model.Reader;

import java.util.List;
@Data
public class AddReadersState {

    private final List<Reader> readerList;
    private final String error;
}
