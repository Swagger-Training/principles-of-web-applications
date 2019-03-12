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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-14T12:00:35.775Z")

@Controller
public class CurrenciesApiController implements CurrenciesApi {

    private static final Logger log = LoggerFactory.getLogger(CurrenciesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @PersistenceContext
    private EntityManager em;

    @org.springframework.beans.factory.annotation.Autowired
    public CurrenciesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Currency> addCurrency(@ApiParam(value = "A currency object", required = true) @Valid @RequestBody Currency body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            em.persist(body);
            return new ResponseEntity<Currency>(body, HttpStatus.OK);

        } else {
            return new ResponseEntity<Currency>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> deleteCurrency(@ApiParam(value = "A currency code", required = true) @PathVariable("code") String code) {
        final Currency currency = em.find(Currency.class, code);
        if (currency != null) {
            em.remove(currency);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Currency>> getCurrencies(@ApiParam(value = "") @Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            final Query q = Optional.ofNullable(name).map(n ->
                    em.createQuery("select c from Currency c where c.id like :name")
                            .setParameter("name", n + "%")
            ).orElse(em.createQuery("select c from Currency c"));
            return new ResponseEntity<List<Currency>>(q.getResultList(), HttpStatus.OK);

        } else {
            return new ResponseEntity<List<Currency>>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<Currency> getCurrency(@ApiParam(value = "A currency code", required = true) @PathVariable("code") String code) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            final Currency currency = em.find(Currency.class, code);
            if (currency != null) {
                return new ResponseEntity<Currency>(currency, HttpStatus.OK);

            } else {
                return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<Currency>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Void> upatesCurrency(@ApiParam(value = "A currency code", required = true) @PathVariable("code") String code, @ApiParam(value = "A currency object", required = true) @Valid @RequestBody Currency body) {
        final Currency currency = em.find(Currency.class, code);
        if (currency != null) {
            currency.setCode(code);
            currency.setName(body.getName());
            currency.setRate(body.getRate());
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

        } else {
            body.setCode(code);
            em.persist(body);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @Transactional
    public ResponseEntity<Currency> updateCurrencyRate(@ApiParam(value = "A currency code", required = true) @PathVariable("code") String code, @ApiParam(value = "A currency rate object", required = true) @Valid @RequestBody Amount currencyRate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            final Currency currency = em.find(Currency.class, code);
            if (currency != null) {
                currency.setRate(currencyRate.getAmount());
                return new ResponseEntity<Currency>(currency, HttpStatus.OK);

            } else {
                return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<Currency>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
