const path = require("path");

// 빌드 폴더를 삭제하거나 비우는 플러그인 : https://www.npmjs.com/package/clean-webpack-plugin
import cleanWebpackPlugin from "clean-webpack-plugin"
// 웹팩 파일들을 하나로 머지해주는 플러그인 : https://www.npmjs.com/package/webpack-merge
import merge from 'webpack-merge'
// 런타임에 외부 종속성을 검색할 수 있도록 해주는 플러그인 : https://www.npmjs.com/package/webpack-node-externals
import nodeExternals from "webpack-node-externals"

const common = require("./webpack.common");

module.exports = merge(common, {
    devtool: "source-map",
    entry: [path.join(__dirname, "dist/index.js")],
    externals: [nodeExternals({})],
    mode: "development",
    plugins: [new cleanWebpackPlugin.CleanWebpackPlugin()],
    watch: true
});