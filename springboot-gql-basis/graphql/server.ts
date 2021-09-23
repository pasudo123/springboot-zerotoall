
const { ApolloServer } = require('apollo-server');
const { ApolloServerPluginLandingPageGraphQLPlayground } = require('apollo-server-core')

// dotenv 가 .env.{profile} 내 데이터를 읽게해준다.
require("dotenv").config({path: `.env.${process.env.NODE_ENV}`});

// restful api datasource 를 추가해준다.
const SpringBootAPI = require("./api/springboot-api.ts");
const typeDefs = require('./schema/index.ts');
const resolvers = require('./resolvers/index.ts');

const server = new ApolloServer({
    typeDefs,
    resolvers,
    dataSources: () => {
        return {
            springbootAPI: new SpringBootAPI()
        }
    },
    plugins: [
        ApolloServerPluginLandingPageGraphQLPlayground(),
    ],
});

server.listen().then(({ url }) => {
    console.log(`🚀 =====================================`)
    console.log(`🚀 Server env : ${process.env.NODE_ENV}`)
    console.log(`🚀 Server name : ${process.env.APP_NAME}`)
    console.log(`🚀 Server ready at ${url}`);
    console.log(`🚀 =====================================`)
});