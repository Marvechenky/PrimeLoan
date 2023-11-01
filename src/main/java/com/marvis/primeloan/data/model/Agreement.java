package com.marvis.primeloan.data.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
public class Agreement {
    private String loanTerms;
    private String repaymentSchedule;

}
