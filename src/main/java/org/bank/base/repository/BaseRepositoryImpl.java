package org.bank.base.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.base.service.BaseServiceImpl;

import javax.persistence.EntityManager;
import java.io.Serializable;

public abstract class BaseRepositoryImpl<E extends Serializable, ID extends Serializable> implements BaseRepository<E,ID> {
   private final EntityManager entityManager;
   public BaseRepositoryImpl (EntityManager entityManager) {
      this.entityManager = entityManager;
   }
   @Override
   public void create (E e) {
      entityManager.persist(e);
   }
   @Override
   public E read (ID id) {
      return entityManager.find(getEntityClass(),id);
   }
   @Override
   public void update (E e) {
      entityManager.merge(e);
   }
   @Override
   public void delete (E e) {
      entityManager.remove(e);
   }
   @Override
   public abstract Class<E> getEntityClass();
}
