package com.allnote.user;

import com.allnote.note.Note;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)//todo check deleting
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Note> notes;

    @CreatedDate
    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<Role> roles = new LinkedList<>();

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
        return roles.stream().flatMap(role -> role.getAuthorities().stream()).collect(Collectors.toList());
    }
}
