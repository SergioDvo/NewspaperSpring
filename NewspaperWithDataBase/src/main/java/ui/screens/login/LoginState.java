package ui.screens.login;

import lombok.Data;

@Data
public class LoginState {

    private final boolean loginOK;
    private final String error;
    private final Integer idUsuario;

}
