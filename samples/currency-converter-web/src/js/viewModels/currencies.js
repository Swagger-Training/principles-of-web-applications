'use strict';

define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojinputtext',
        'ojs/ojinputtext', 'ojs/ojbutton', 'ojs/ojarraytabledatasource', 'ojs/ojtable'],
    function (oj, ko, $) {

        function CurrenciesViewModel() {
            var self = this;

            self.code = ko.observable();
            self.name = ko.observable();
            self.rate = ko.observable(1.0);

            self.currencies = ko.observableArray([]);
            self.currenciesDataSource = new oj.ArrayTableDataSource(self.currencies, {
                idAttribute: 'code'
            });

            self.save = function () {
                var newCurrency = {
                    code: self.code(),
                    name: self.name(),
                    rate: self.rate()
                };
                var currencies = self.currencies();
                var foundCurrency = currencies.find(function (currency) {
                    return currency.code === newCurrency.code;
                });

                // update
                fetch('http://localhost:8080/v1/currencies/' + newCurrency.code, {
                    body: JSON.stringify(newCurrency),
                    method: 'PUT',
                    headers: {
                        'content-type': 'application/json'
                    }

                }).then(function(response) {
                    if (response.ok) {
                        self.fetchCurrencies().then(function (fetchedCurrencies) {
                            self.currencies(fetchedCurrencies);
                        });

                    } else {
                        throw Error('failed to save currency: ' + foundCurrency.code);
                    }
                });
            };

            self.remove = function (currency) {
                fetch('http://localhost:8080/v1/currencies/' + currency.code, {
                    method: 'DELETE',
                }).then(function(response) {
                    if (response.ok) {
                        self.fetchCurrencies().then(function(fetchedCurrencies) {
                            self.currencies(fetchedCurrencies);
                        });
                    }
                });
            };

            self.selected = function (evt) {
                if (evt.detail.value) {
                    var selectedIndex = evt.detail.value[0].startIndex.row;
                    var selectedCurrency = self.currencies()[selectedIndex];
                    self.code(selectedCurrency.code);
                    self.name(selectedCurrency.name);
                    self.rate(selectedCurrency.rate);

                } else {
                    self.code('');
                    self.name('');
                    self.rate(0.0);
                }
            };

            self.connected = function () {
                self.fetchCurrencies().then(function (currencies) {
                    self.currencies(currencies);
                });
            };

            self.fetchCurrencies = function () {
                return fetch('http://localhost:8080/v1/currencies', {
                    cache: 'no-cache',
                    credentials: 'same-origin',
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
                });
            };
        }

        /*
         * Returns a constructor for the ViewModel so that the ViewModel is constructed
         * each time the view is displayed.  Return an instance of the ViewModel if
         * only one instance of the ViewModel is needed.
         */
        return new CurrenciesViewModel();
    }
);
