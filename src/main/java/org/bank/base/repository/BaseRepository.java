package org.bank.base.repository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public interface BaseRepository<E extends Serializable,ID extends Serializable>{

    void create(E e);
    E read (ID id);
    void update (E e);
    void delete (E e);
    Class<E> getEntityClass();
}
