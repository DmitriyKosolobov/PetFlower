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
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount")
    private List<Device> devices = new ArrayList<>();

    @OneToOne(mappedBy = "userAccount")
    private UserAccountInfo userAccountInfo;

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
        pet.setUserAccount(this);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
        pet.setUserAccount(null);
    }

    public void addDevice(Device device) {
        this.devices.add(device);
        device.setUserAccount(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.setUserAccount(null);
    }

    public void addUserAccountInfo(UserAccountInfo userAccountInfo) {
        this.userAccountInfo = userAccountInfo;
        userAccountInfo.setUserAccount(this);
    }

    public void removeUserAccountInfo(UserAccountInfo userAccountInfo) {
        this.userAccountInfo = null;
        userAccountInfo.setUserAccount(null);
    }

}
