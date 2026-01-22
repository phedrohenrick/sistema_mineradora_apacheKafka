package org.br.mineradora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Jacksonized
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityDTO {

    @JsonProperty("proposalId")
    private Long proposalId;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("priceTonne")
    private BigDecimal priceTonne;

    private BigDecimal lastDollarQuotation;

}
