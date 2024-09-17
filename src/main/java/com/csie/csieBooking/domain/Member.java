package com.csie.csieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String studentId;
    private String name;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();

}
