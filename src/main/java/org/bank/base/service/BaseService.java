package org.bank.base.service;

import org.bank.base.entity.BaseEntity;
import org.bank.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService <E extends BaseEntity ,ID extends Serializable, R extends BaseRepository<E,ID>> {
    void save(E e);
    Optional<E> load(ID id);
    void update(E e);
    void delete(E e);
}
