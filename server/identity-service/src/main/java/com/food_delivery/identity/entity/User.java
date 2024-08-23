package com.food_delivery.identity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;

    @Column(name = "is_email_verified")
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    @Builder.Default
    private String imageUrl = "https://res.cloudinary.com/dxvqk2zqk/image/upload/v1681882949/food-delivery/default/default_user_2x_jkqj2o.png";

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<VerifyOtp> verifyOtp;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Address> addresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

//    @Override
//    public Integer getId() {
//        return id;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
