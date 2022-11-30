package ui.screens.updateReaders;

import lombok.Data;
import model.Reader;

import java.util.List;
@Data
public class UpdateReadersState {
    private final List<Reader> readerList;
    private final String error;
}
