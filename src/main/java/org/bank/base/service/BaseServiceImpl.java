package org.bank.base.service;

import org.bank.base.entity.BaseEntity;
import org.bank.base.repository.BaseRepository;

import java.io.Serializable;

public class BaseServiceImpl<E extends BaseEntity, ID extends Serializable, R extends BaseRepository<E, ID>> implements BaseService<E, ID, R> {
    BaseRepository<E,ID> baseRepository;
    public BaseServiceImpl(BaseRepository<E,ID> baseRepository) {
        this.baseRepository = baseRepository;
    }
    @Override
    public void save(E e) {
        baseRepository.create(e);
    }

    @Override
    public E load(ID id) {
        return baseRepository.read(id);
    }

    @Override
    public void update(E e) {
        baseRepository.update(e);
    }

    @Override
    public void delete(E e) {
        baseRepository.delete(e);
    }
}
