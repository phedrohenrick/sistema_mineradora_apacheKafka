package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.DTO.ProposalDetailsDTO;



public interface ProposalService {

    ProposalDetailsDTO getProposalDetailsById(@PathParam("id") long proposalId);

    Response createProposal(ProposalDetailsDTO proposalDetails);

    Response removeProposal(long id);

}