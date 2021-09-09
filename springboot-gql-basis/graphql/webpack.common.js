const path = require("path");

module.exports = {
    output: {
        filename: "index.js",
        path: path.resolve(__dirname, "dist")
    },
    resolve: {
        extensions: [".js"]
    },
    target: "node"
};