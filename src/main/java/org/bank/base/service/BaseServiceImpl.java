package org.bank.base.service;

import org.bank.base.entity.BaseEntity;
import org.bank.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class BaseServiceImpl<E extends BaseEntity, ID extends Serializable, R extends BaseRepository<E, ID>> implements BaseService<E, ID, R> {
    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public void save(E e) {
        execute(e, repository::create);

    }

    @Override
    public Optional<E> load(ID id) {
        return repository.read(id);
    }

    @Override
    public void update(E e) {
        execute(e, repository::update);

    }


    @Override
    public void delete(E e) {
        execute(e, repository::delete);
    }
    @Override
    public boolean isExist (ID id) {
        return repository.read(id).isPresent();
    }

    private void execute(E e, Consumer<E> consumer) {
        try {
            repository.getEntityManager().getTransaction().begin();
            consumer.accept(e);
            repository.getEntityManager().getTransaction().commit();
        } catch (Exception ex) {
            repository.getEntityManager().getTransaction().rollback();
            throw ex;
        }
    }
}
