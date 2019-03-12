#!/usr/bin/env node

const express = require('express');

const app = express();

app.get('/', function(req, res) {
    res.redirect('/nobody');
});

app.get('/:name', function(req, res) {
    res.json({
        msg: `hello ${req.params.name}!`
    });
});

app.listen(3000, function() {
    console.log('server listening on port 3000');
});
