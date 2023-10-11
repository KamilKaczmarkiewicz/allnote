package com.allnote.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String username;

    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_picture_path")
    private String profilePicturePath;

    @CreatedDate
    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime lastModifiedDate;


    @Column(name = "is_account_non_expired")
    @Builder.Default
    private boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked")
    @Builder.Default
    private boolean isAccountNonLocked = true;

    @Column(name = "is_credentials_non_expired")
    @Builder.Default
    private boolean isCredentialsNonExpired = true;

    @Column(name = "is_enabled")
    @Builder.Default
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
