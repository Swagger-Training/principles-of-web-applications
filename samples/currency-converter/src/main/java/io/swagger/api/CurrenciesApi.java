/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Amount;
import io.swagger.model.Currency;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-14T12:00:35.775Z")

@Api(value = "currencies", description = "the currencies API")
@CrossOrigin(origins = "http://localhost:8000")
public interface CurrenciesApi {

    @ApiOperation(value = "Add currency", nickname = "addCurrency", notes = "Creates a new currency", response = Currency.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "internal", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The created currency", response = Currency.class),
        @ApiResponse(code = 400, message = "Invalid Currency/Already exists") })
    @RequestMapping(value = "/currencies",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Currency> addCurrency(@ApiParam(value = "A currency object" ,required=true )  @Valid @RequestBody Currency body);


    @ApiOperation(value = "Delete currency", nickname = "deleteCurrency", notes = "Deletes a currency", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "internal", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successfully deleted currency"),
        @ApiResponse(code = 404, message = "Currency Not Found") })
    @RequestMapping(value = "/currencies/{code}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCurrency(@ApiParam(value = "A currency code",required=true) @PathVariable("code") String code);


    @ApiOperation(value = "Get currencies", nickname = "getCurrencies", notes = "Gets all currencies", response = Currency.class, responseContainer = "List", tags={ "public", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesfully retreived currencies", response = Currency.class, responseContainer = "List") })
    @RequestMapping(value = "/currencies",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Currency>> getCurrencies(@ApiParam(value = "") @Valid @RequestParam(value = "name", required = false) String name);


    @ApiOperation(value = "Get currency", nickname = "getCurrency", notes = "Gets a currency by code", response = Currency.class, tags={ "public", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully retreived currency", response = Currency.class),
        @ApiResponse(code = 404, message = "Currency Not Found") })
    @RequestMapping(value = "/currencies/{code}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Currency> getCurrency(@ApiParam(value = "A currency code",required=true) @PathVariable("code") String code);


    @ApiOperation(value = "Update currency", nickname = "upatesCurrency", notes = "Crteates or updates a currency", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "internal", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The created currency"),
        @ApiResponse(code = 400, message = "Invalid Currency"),
        @ApiResponse(code = 404, message = "Currency not found") })
    @RequestMapping(value = "/currencies/{code}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> upatesCurrency(@ApiParam(value = "A currency code",required=true) @PathVariable("code") String code,@ApiParam(value = "A currency object" ,required=true )  @Valid @RequestBody Currency body);


    @ApiOperation(value = "Update currency rate", nickname = "updateCurrencyRate", notes = "Updates the current rate of an existing currency", response = Currency.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "internal", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The created currency", response = Currency.class),
        @ApiResponse(code = 400, message = "Invalid Currency"),
        @ApiResponse(code = 404, message = "Currency not found") })
    @RequestMapping(value = "/currencies/{code}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PATCH)
    ResponseEntity<Currency> updateCurrencyRate(@ApiParam(value = "A currency code",required=true) @PathVariable("code") String code,@ApiParam(value = "A currency rate object" ,required=true )  @Valid @RequestBody Amount currencyRate);

}
