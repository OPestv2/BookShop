package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.util.AuthorityPK;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@IdClass(AuthorityPK.class)
@Entity
@Getter
@Setter
@Table(name = "authorities")
public class Authority {

    @Id
    private Long id;

    @Id
    private String authority;

    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private Customer user;

    public Authority(){}

    public Authority(Customer user, String authority) {
        this.id = user.getId();
        this.authority = authority;
        this.user = user;
    }
}
