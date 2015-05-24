/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LiH
 */
@Entity
@Table(name = "integer_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntegerRequest.findAll", query = "SELECT i FROM IntegerRequest i ORDER BY i.id ASC"),
    @NamedQuery(name = "IntegerRequest.findById", query = "SELECT i FROM IntegerRequest i WHERE i.id = :id"),
    @NamedQuery(name = "IntegerRequest.findByIntegers", query = "SELECT i FROM IntegerRequest i WHERE i.integer1 = :integer1 and i.integer2 = :integer2")})

public class IntegerRequest implements Serializable {

    private static final long serialVersionUID = -6456237218120116398L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "integer1")
    private Integer integer1;

    @Column(name = "integer2")
    private Integer integer2;

    @OneToOne(mappedBy = "integerRequest")
    private Gcd gcd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Calendar lastUpdated;

    public IntegerRequest() {
    }

    public IntegerRequest(Integer integer1, Integer integer2) {
        this.integer1 = integer1;
        this.integer2 = integer2;
    }

    public IntegerRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }

    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }

    public Calendar getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Calendar lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @PrePersist
    @PreUpdate
    public void setLastUpdated() {
        lastUpdated = Calendar.getInstance();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.integer1 != null ? this.integer1.hashCode() : 0);
        hash = 59 * hash + (this.integer2 != null ? this.integer2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntegerRequest other = (IntegerRequest) obj;
        if (this.integer1 == null || !this.integer1.equals(other.integer1)) {
            return false;
        }
        if (this.integer2 == null || !this.integer2.equals(other.integer2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lhh.demo.model.IntegerRequest[ id=" + id + " ]";
    }

}
