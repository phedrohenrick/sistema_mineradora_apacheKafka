package org.br.mineradora.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.DTO.OpportunityDTO;
import org.br.mineradora.client.ReportRestClient;
import org.br.mineradora.utils.CSVHelper;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
@Traced
public class ReportServiceImpl implements ReportService {

    @Inject
    @RestClient
    ReportRestClient reportRestClient;

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {

        List<OpportunityDTO> opportunityData = reportRestClient.requestOpportunitiesData();
        return CSVHelper.OpportunitiesToCSV(opportunityData);

    }

    @Override
    public List<OpportunityDTO> getOpportunitiesData() {
        System.out.println("teste--------------service");
        return reportRestClient.requestOpportunitiesData();

    }

}