package org.br.mineradora.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@Jacksonized
public class ProposalDetailsDTO {

    private Long proposalId;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("priceTonnes")
    private BigDecimal priceTonnes;

    @JsonProperty("tonnes")
    private Integer tonnes;

    @JsonProperty("country")
    private String country;

    @JsonProperty("validityDays")
    private Integer proposalValidityDays;

}