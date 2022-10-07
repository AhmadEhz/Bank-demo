package org.bank.base.service;

import org.bank.base.entity.BaseEntity;
import org.bank.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends BaseEntity, ID extends Serializable, R extends BaseRepository<E, ID>> implements BaseService<E, ID, R> {
    protected BaseRepository<E,ID> repository;
    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }
    @Override
    public void save(E e) {
        repository.create(e);
    }

    @Override
    public Optional<E> load(ID id) {
        return repository.read(id);
    }

    @Override
    public void update(E e) {
        repository.update(e);
    }

    @Override
    public void delete(E e) {
        repository.delete(e);
    }
}
