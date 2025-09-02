package com.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tblUsers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    private String userEmail;

    @Column(nullable = false,length = 100)
    private String userName;

    private boolean enabled = true;

    @Column(nullable = false)
    private String password;

    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblUserRoles",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @PrePersist
    private void prePersist(){
        this.createdDate = LocalDate.now();
    }






}
