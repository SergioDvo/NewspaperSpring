package services;

import dao.DaoType;
import jakarta.inject.Inject;
import model.Type;

import java.util.ArrayList;
import java.util.List;

public class ServicesType implements services.impl.ServiciosTypeImpl {

    private final DaoType daoType;

    @Inject
    public ServicesType(DaoType daoType) {
        this.daoType = daoType;
    }


    @Override
    public List<Integer> getAllIds() {
        List<Integer> allIds = new ArrayList<>();
        for (int i = 0; i < daoType.getAll().size(); i++) {
            allIds.add(daoType.getAll().get(i).getId());
        }
        return allIds;
    }
    @Override
    public List<Type> getAll() {
        return daoType.getAll();
    }
}
