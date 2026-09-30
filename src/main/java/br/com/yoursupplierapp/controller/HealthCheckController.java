package br.com.yoursupplierapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping(value = "/api-bridge/health-check", method = RequestMethod.GET, produces = { "application/json"})
    @Operation(description = "Request to intercept" , responses = {
            @ApiResponse(description = "Request has been forwarded", responseCode = "200", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "There was an error forwarding the request", responseCode = "500")})
    public ResponseEntity<String> HealthCheck() {
        return new ResponseEntity<>("I'm alive - Seggi App!", HttpStatus.OK);
    }

}
