'use strict';

define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojselectcombobox', 'ojs/ojinputtext', 'ojs/ojinputnumber'],
    function (oj, ko, $) {

        function ConverterViewModel() {
            var self = this;

            self.currencies = ko.observableArray([]);
            self.amount = ko.observable(0);
            self.selectedInputCurrencyIdx = ko.observable(0);
            self.selectedInputCurrencyCode = ko.pureComputed(function() {
                return self.currencies()[self.selectedInputCurrencyIdx()] ?
                    self.currencies()[self.selectedInputCurrencyIdx()].code : 'EUR';
            });
            self.selectedOutputCurrencyIdx = ko.observable(0);
            self.selectedOutputCurrencyCode = ko.pureComputed(function() {
                return self.currencies()[self.selectedOutputCurrencyIdx()] ?
                    self.currencies()[self.selectedOutputCurrencyIdx()].code : 'EUR';
            });

            self.currencyOptions = ko.pureComputed(function() {
                return self.currencies().map(function (currency, idx) {
                    return {
                        value: idx,
                        label: currency.name
                    };
                });
            });

            self.convertedAmount = ko.pureComputed(function() {
                var currencies = self.currencies();
                if (currencies.length > 0) {
                    return self.amount() / currencies[self.selectedInputCurrencyIdx()].rate *
                        currencies[self.selectedOutputCurrencyIdx()].rate;

                } else {
                    return 0;
                }
            });

            self.connected = function() {
                fetch('http://localhost:8080/v1/currencies', {
                    cache: 'no-cache',
                    method: 'GET',
                    headers: {
                        'accept': 'application/json'
                    }

                }).then(function (response) {
                    if (response.ok) {
                        return response.json();

                    } else {
                        throw Error('failed to get currencies');
                    }

                }).then(function (currencies) {
                    self.currencies(currencies);
                });
            };
        }

        return new ConverterViewModel();
    }
);
