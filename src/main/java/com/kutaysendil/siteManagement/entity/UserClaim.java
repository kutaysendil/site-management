
package com.kutaysendil.siteManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_claims")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserClaim extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;
}