package ru.petflower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "userAccount")
    private List<Device> devices = new ArrayList<>();

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setUserAccount(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setPlant(null);
    }

}
