package ui.screens.login;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import services.ServicesLogin;

public class LoginViewModel {


    private final ServicesLogin servicesLogin;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesLogin servicesLogin) {
        this.servicesLogin = servicesLogin;
        state = new SimpleObjectProperty<>(new LoginState(false,null,null));
    }
    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }
    public void doLogin(String username,String password) {
        int idUsuario = servicesLogin.doLogin(username,password);
        if (idUsuario!=-17)
        {
            state.setValue(new LoginState(true,null,idUsuario));
            //TODO hacer las pantallas de los usuarios
            //TODO añadir el LOGIN al reader
        }
        else
        {
            state.setValue(new LoginState(false,"ese correo no esta registrado, use root root",null));
        }
    }
}
