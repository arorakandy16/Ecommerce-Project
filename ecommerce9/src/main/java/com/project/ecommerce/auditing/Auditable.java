package com.project.ecommerce.auditing;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


/*
  Create Generic Auditable Class with Spring Data Annotations
  @CreatedBy, @CreatedDate, @LastModifiedBy, and @LastModifiedDate
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
/*
    Using the AuditingEntityListener Class With @EntityListeners
 */
public abstract class Auditable<String>{

    @CreatedBy
    protected java.lang.String createdBy;


    @CreatedDate

    @Temporal(TemporalType.DATE)
    protected Date createdDate;


    @LastModifiedBy
    protected java.lang.String lastModifiedBy;


    @LastModifiedDate

    @Temporal(TemporalType.TIMESTAMP)
    protected java.util.Date lastModifiedDate;




    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(java.lang.String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}