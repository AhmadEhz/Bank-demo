package org.bank.base.repository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

public abstract class BaseRepositoryImpl<E extends Serializable, ID extends Serializable> implements BaseRepository<E,ID> {
   protected final EntityManager entityManager;
   public BaseRepositoryImpl (EntityManager entityManager) {
      this.entityManager = entityManager;
   }
   @Override
   public void create (E e) {
      entityManager.persist(e);
   }
   @Override
   public Optional<E> read (ID id) {
      return Optional.ofNullable(entityManager.find(getEntityClass(),id));
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
   @Override
   public EntityManager getEntityManager() {
      return entityManager;
   }
   
}
