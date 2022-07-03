package org.example.util;

import java.io.Serializable;
import java.util.Objects;
public class AuthorityPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String authority;
    public AuthorityPK() {
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityPK that = (AuthorityPK) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(authority, that.authority);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
}