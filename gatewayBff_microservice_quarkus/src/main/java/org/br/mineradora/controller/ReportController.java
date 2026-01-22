package org.br.mineradora.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.service.ReportService;

import java.util.Date;

@ApplicationScoped
@Path("/api/opportunity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReportController {

    @Inject
    ReportService reportService;

    @GET
    @Path("/report")
    @RolesAllowed({"user","manager"})
    public Response generateReport(){

        try {

            return Response.ok(reportService.generateCSVOpportunityReport(),
                            MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-disposition",
                            "attachment; filename = "+ new Date() +"--oportunidades-venda.csv").
                    build();

        } catch (ServerErrorException errorException) {

            return Response.serverError().build();

        }

    }

    @GET
    @Path("/data")
    @RolesAllowed({"user","manager"})
    public Response generateOpportunitiesData(){


        try {
            System.out.println("teste--------------controller");
            return Response.ok(reportService.getOpportunitiesData(),
                    MediaType.APPLICATION_JSON).build();

        } catch (ServerErrorException errorException) {

            return Response.serverError().build();

        }

    }

}