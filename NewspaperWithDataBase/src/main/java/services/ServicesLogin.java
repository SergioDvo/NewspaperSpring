package services;

import dao.DaoLogin;
import jakarta.inject.Inject;

public class ServicesLogin implements services.impl.ServiciosClientesImpl {

    private final DaoLogin daoLogin;

    @Inject
    public ServicesLogin(DaoLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public int doLogin(String username, String password) {
        return daoLogin.getReader(username, password);
    }

}
