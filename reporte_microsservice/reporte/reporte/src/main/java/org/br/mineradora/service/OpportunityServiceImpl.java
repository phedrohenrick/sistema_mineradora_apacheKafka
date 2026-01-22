package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.OpportinityEntity;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.repository.OpportunityRepository;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@ApplicationScoped
@Traced
public class OpportunityServiceImpl implements OpportunityService{

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    OpportunityRepository opportunityRepository;

    @Override
    @Transactional
    public void buildOpportunity(ProposalDTO proposalDTO) {

        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();

        Collections.reverse(quotationEntities); //traz a cotação do dolar mais atual para a posição 0

        OpportinityEntity opportinityEntity = new OpportinityEntity();
        opportinityEntity.setDate(new Date());
        opportinityEntity.setProposalId(proposalDTO.getProposalId());
        opportinityEntity.setCustomer(proposalDTO.getCustomer());
        opportinityEntity.setPriceTonne(proposalDTO.getPriceTonnes());
        opportinityEntity.setLastDollarQuotation(quotationEntities.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportinityEntity);
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationDTO quotationDTO) {

        QuotationEntity createQuotation = new QuotationEntity();
        createQuotation.setDate(new Date());
        createQuotation.setCurrencyPrice(quotationDTO.getCurrencyPrice());

        quotationRepository.persist(createQuotation);
    }



    @Override
    public List<OpportunityDTO> generateOpportunityData() {


        List<OpportunityDTO> opportunities = new ArrayList<>();

        opportunityRepository
                .findAll()
                .stream()
                .forEach(item->{
                    opportunities.add(OpportunityDTO.builder()
                            .proposalId(item.getProposalId())
                            .customer(item.getCustomer())
                            .priceTonne(item.getPriceTonne())
                            .lastDollarQuotation(item.getLastDollarQuotation())
                            .build());
                });

        return opportunities;

    }

}
