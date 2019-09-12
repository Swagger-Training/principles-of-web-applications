#!/usr/bin/env node

const express = require('express');

const app = express();

const currencies = [
    {
        code: 'EUR',
        name: 'Euro',
        rate: '1.0'
    },
    {
        code: 'USD',
        name: 'US Dollar',
        rate: '1.15'
    }
];

app.get('/currencies', function(request, response) {
    response.json(currencies.filter(currency =>
        currency.name.indexOf(request.query.name) > -1));
});

app.get('/currencies/:code', function(request, response) {
    response.json(
        currencies.find(function(currency) {
            return currency.code === request.params.code;
        }));
});

app.get('/convert', function() {

});

app.listen(3000, function() {
    console.log('server listens on port 3000');
});
