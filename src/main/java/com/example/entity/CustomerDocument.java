package com.example.entity;

import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentID;

    @OneToOne
    private Customer customer;

    @Lob
    private byte[] addressProof;
    private Boolean addressProofVerified = false;

    @Lob
    private byte[] panCard;
    private Boolean panCardVerified = false;

    @Lob
    private byte[] incomeTax;
    private Boolean incomeTaxVerified = false;

    @Lob
    private byte[] addharCard;
    private Boolean addharCardVerified = false;

    @Lob
    private byte[] photo;
    private Boolean photoVerified = false;

    @Lob
    private byte[] signature;
    private Boolean signatureVerified = false;

    @Lob
    private byte[] bankCheque;
    private Boolean bankChequeVerified = false;

    @Lob
    private byte[] salarySlips;
    private Boolean salarySlipsVerified = false;
}
