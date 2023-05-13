const express = require("express");

var httpProxy = require("http-proxy");

const path = require("path");

var apiProxy = httpProxy.createProxyServer();

const app = express();

const port = process.env.PORT || 3000;

const backendHost = process.env.BACKEND_ENDPOINT || "http://localhost:8080";

const fileDirectory = path.resolve(__dirname, "./dist/my-angular");

app.use(express.static(fileDirectory));

app.all("/api/*", function(req, res){
apiProxy.web(req, res, {target: backendHost})

});

app.listen(port, () => {

console.log(`BACKEND_ENDPOINT: ${process.env.BACKEND_ENDPOINT}`);

console.log(`Backend Point: ${backendHost}`);

console.log(`App listening at ${port}`);

});
