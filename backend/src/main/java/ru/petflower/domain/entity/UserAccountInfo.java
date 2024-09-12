package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserAccountInfo {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String info;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    public UserAccountInfo(String info) {
        this.info = info;
    }
}
