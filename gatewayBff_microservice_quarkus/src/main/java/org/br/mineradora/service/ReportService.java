package org.br.mineradora.service;


import org.br.mineradora.DTO.OpportunityDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportService {

    ByteArrayInputStream generateCSVOpportunityReport();

    List<OpportunityDTO> getOpportunitiesData();

}