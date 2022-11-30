package ui.screens.readersList;

import lombok.Data;
import model.Reader;

import java.util.List;
@Data
public class ReadersListState {

    private final List<Reader> readerList;
    private final String error;
}
