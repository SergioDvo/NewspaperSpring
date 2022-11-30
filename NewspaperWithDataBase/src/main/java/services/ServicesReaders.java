package services;

import dao.DaoReadArticle;
import dao.DaoReaders;
import dao.DaoSubscribe;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.*;

import java.time.LocalDate;
import java.util.List;

public class ServicesReaders implements services.impl.ServiciosReadersImpl {

    private final DaoReaders daoReaders;
    private final ServicesArticle servicesArticle;
    private final DaoSubscribe daoSubscribe;
    private final DaoReadArticle daoReadArticle;

    @Inject
    public ServicesReaders(DaoReaders daoReaders, ServicesArticle servicesArticle, DaoSubscribe daoSubscribe,DaoReadArticle daoReadArticle) {
        this.servicesArticle = servicesArticle;
        this.daoReaders = daoReaders;
        this.daoSubscribe = daoSubscribe;
        this.daoReadArticle=daoReadArticle;
    }
    @Override
    public int addReader(Reader r) {
        return daoReaders.save(r);
    }
    public int updateReader(Reader r) {
        return daoReaders.update(r);
    }

    @Override
    public Either<String, List<Reader>> getReadersList() {
        return daoReaders.getAll();
    }

    @Override
    public List<Reader> getReadersListByArticleType(int type) {

        return daoReaders.getAll(type).get();
    }
    @Override
    public boolean addReaderArticle(Reader reader, Article article, int rating){
        return daoReadArticle.saveReadArticle(reader, article, rating) == 1;
    }
    public List<Reader> getReadersListByNewspaperDate(){
        return daoReaders.counterReadersByNewspaperQuery();
    }
    @Override
    public void deleteReader(Reader reader){
        daoReaders.delete(reader.getId());
    }
    @Override
    public List<Reader> getReadersListByNewspaper(Newspaper newspaper) {
        return daoReaders.getAll(newspaper).get();
    }
    public int counterReaders(int idArticle){
        return daoReaders.counterReadersQuery(idArticle);
    }

    public Either<String, List<Subscription>> getSubscriptionsList(int idReader) {
        return daoSubscribe.getAll(idReader);
    }
    public int addSubscription(Subscription subscription){
        return daoSubscribe.save(subscription);
    }
    public int cancelSubscription(Subscription subscription){
        subscription.setCancellationDate(LocalDate.now());
        return daoSubscribe.update(subscription);
    }

}
