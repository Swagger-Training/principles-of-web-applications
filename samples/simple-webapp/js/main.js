var ViewModel = function() {
    var self = this;
    self.name = ko.observable();
    self.bigName = ko.observable();

    self.greet = function() {
        self.bigName(self.name().toUpperCase());
    }
};

ko.applyBindings(new ViewModel());
