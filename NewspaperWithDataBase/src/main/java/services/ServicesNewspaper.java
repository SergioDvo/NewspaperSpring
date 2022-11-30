package services;

import dao.DaoNewspaper;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;

public class ServicesNewspaper implements services.impl.ServiciosNewspaperImpl {

    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }

    @Override
    public List<Newspaper> getNewspaperList(){
        return daoNewspaper.getAll();
    }

    @Override
    public boolean addNewspaper(Newspaper newspaper){
        if (daoNewspaper.getAll().contains(newspaper)) {
            return false;
        }else {
            daoNewspaper.save(newspaper);
            return true;
        }
    }

    @Override
    public boolean deleteNewspaper(Newspaper newspaper){
        if (daoNewspaper.getAll().contains(newspaper)) {
            daoNewspaper.delete(newspaper);
            return true;
        }else {
            return false;
        }
    }
    public boolean updateNewspaper(Newspaper newspaper){
        return daoNewspaper.update(newspaper) > 0;
    }


}
