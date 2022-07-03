package org.example.entity;

import org.example.util.AuthorityPK;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@IdClass(AuthorityPK.class)
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    private int id;
    @Id
    private String authority;
    @ManyToOne
    @JoinColumn(name = "username", updatable = false, insertable = false)
    private Customer user;
    public Authority() {
    }
    public Authority(Customer user, String authority) {
        this.id = user.getId();
        this.authority = authority;
        this.user = user;
    }
    public int getUsername() {
        return id;
    }
    public void setUsername(int username) {
        this.id = username;
    }
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    public Customer getUser() {
        return user; }
    public void setUser(Customer user) {
        this.user = user;
    }
}
