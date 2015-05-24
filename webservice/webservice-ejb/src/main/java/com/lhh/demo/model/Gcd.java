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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LiH
 */
@Entity
@Table(name = "gcd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gcd.findAll", query = "SELECT g FROM Gcd g ORDER BY g.id ASC"),
    @NamedQuery(name = "Gcd.findById", query = "SELECT g FROM Gcd g WHERE g.id = :id"),
    @NamedQuery(name = "Gcd.findByGcd", query = "SELECT g FROM Gcd g WHERE g.gcd = :gcd"),

    @NamedQuery(name = "Gcd.findByIntegerRequest", query = "SELECT g FROM Gcd g WHERE g.integerRequest = :integerRequest")})
public class Gcd implements Serializable {

    private static final long serialVersionUID = 8301642463361954198L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "gcd")
    private Integer gcd;

    @NotNull(message = "{IntegerRequest.notNull}")
    @JoinColumn(name = "integer_request", referencedColumnName = "id")
    @OneToOne
    private IntegerRequest integerRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Calendar lastUpdated;

    public Gcd() {
    }

    public Gcd(Integer gcd, IntegerRequest integerRequest) {

        this.gcd = gcd;
        this.integerRequest = integerRequest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGcd() {
        return gcd;
    }

    public void setGcd(Integer gcd) {
        this.gcd = gcd;
    }

    public IntegerRequest getIntegerRequest() {
        return integerRequest;
    }

    public void setIntegerRequest(IntegerRequest integerRequest) {
        this.integerRequest = integerRequest;
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
      lastUpdated =  Calendar.getInstance();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.integerRequest != null ? this.integerRequest.hashCode() : 0);
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
        final Gcd other = (Gcd) obj;
        if (this.integerRequest == null || !this.integerRequest.equals(other.integerRequest)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lhh.demo.model.Gcd[ id=" + id + " ]";
    }

}
