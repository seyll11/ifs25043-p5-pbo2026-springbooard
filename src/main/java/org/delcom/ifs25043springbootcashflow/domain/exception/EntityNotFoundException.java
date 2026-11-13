package org.delcom.ifs25043springbootcashflow.domain.exception;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " dengan ID " + id + " tidak ditemukan");
    }
}
