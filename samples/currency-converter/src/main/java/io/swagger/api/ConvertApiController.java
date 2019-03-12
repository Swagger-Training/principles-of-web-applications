package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Amount;
import io.swagger.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-14T12:00:35.775Z")

@Controller
public class ConvertApiController implements ConvertApi {

    private static final Logger log = LoggerFactory.getLogger(ConvertApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @PersistenceContext
    private EntityManager em;

    @org.springframework.beans.factory.annotation.Autowired
    public ConvertApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Amount> convert(@NotNull @ApiParam(value = "The amount to convert", required = true) @Valid @RequestParam(value = "amount", required = true) Double amount, @NotNull @ApiParam(value = "Code of the input curency", required = true) @Valid @RequestParam(value = "inputCurrency", required = true) String inputCurrency, @NotNull @ApiParam(value = "Code of the output curency", required = true) @Valid @RequestParam(value = "outputCurrency", required = true) String outputCurrency) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            final Currency ic = em.find(Currency.class, inputCurrency);
            final Currency oc = em.find(Currency.class, outputCurrency);
            if (ic != null && oc != null) {
                final Amount convertedAmount = new Amount().amount(amount / ic.getRate() * oc.getRate());
                return new ResponseEntity<Amount>(convertedAmount, HttpStatus.OK);
            } else {
                return new ResponseEntity<Amount>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<Amount>(HttpStatus.NOT_IMPLEMENTED);
    }

}
