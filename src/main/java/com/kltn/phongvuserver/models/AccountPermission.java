package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="account_permission")
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountPermission implements Serializable {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @OneToMany(targetEntity = Account.class, mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Account> accounts = new HashSet<>();
}
