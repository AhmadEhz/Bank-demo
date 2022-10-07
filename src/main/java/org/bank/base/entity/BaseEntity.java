package org.bank.base.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    protected BaseEntity(){}
}
