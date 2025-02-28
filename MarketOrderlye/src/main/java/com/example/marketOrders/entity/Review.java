package com.example.marketOrders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Review {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private Byte grade;

    @Column
    private String imageUrl;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Customer customer;
}
