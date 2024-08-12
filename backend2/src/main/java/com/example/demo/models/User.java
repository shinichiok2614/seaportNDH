package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     private String firstname;
     private String lastname;
     private String address;
     private String department;
     @Column(name = "password", length = 200,nullable = false)
     private String password;
     @Column(name = "is_active")
     private int isActive;
     @Column(name = "date_of_birth")
     private Date dateOfBirth;
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;
    @Column(name = "facebook_account_id")
    private int facebookAccountId;
    @Column(name = "google_account_id")
    private int googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRole().getName().toUpperCase()));
//        authorityList.add(new SimpleGrantedAuthority("ADMIN"));
//        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorityList;
    }

    @Override
    public String getUsername() { //mac dinh cua Spring dung truong Username de dang nhap
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() { //thoi han: vo thoi han
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //khoa user: khong cho khoa
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { //mo khoa user //mac dinh cu true het co phuong thuc
        return true;
    }
}

