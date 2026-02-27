package com.foodibd.backend.entity;

import com.foodibd.backend.entity.embedded.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer riderID;

    private String name;
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "location_street")),
        @AttributeOverride(name = "city",       column = @Column(name = "location_city")),
        @AttributeOverride(name = "state",      column = @Column(name = "location_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "location_postal_code")),
        @AttributeOverride(name = "country",    column = @Column(name = "location_country"))
    })
    private Address currentLocation;
}
