import {ServerInfo} from "apollo-server";
import {formatterError} from "./error/formatter";
import {NoticeAPI} from "./api/notice";
import {ItemTagAPI} from "./api/item-tag";
import {ItemAPI} from "./api/item";
import {typeDefs} from "./schema";
import {resolvers} from "./resolvers";

const { ApolloServer } = require('apollo-server');
const { ApolloServerPluginLandingPageGraphQLPlayground } = require('apollo-server-core')

// dotenv 가 .env.{profile} 내 데이터를 읽게해준다.
require("dotenv").config({path: `.env.${process.env.NODE_ENV}`});

const server = new ApolloServer({
    typeDefs,
    resolvers,
    formatError: formatterError,
    dataSources: () => {
        return {
            noticeAPI: new NoticeAPI(),
            itemTagAPI: new ItemTagAPI(),
            itemAPI: new ItemAPI()
        }
    },
    plugins: [
        ApolloServerPluginLandingPageGraphQLPlayground(),
    ],
    debug: false // 응답결과에 에러 스택트레이스 제외
});

server.listen().then((serverInfo:  ServerInfo) => {
    console.log(`🚀 =====================================`)
    console.log(`🚀 Server env : ${process.env.NODE_ENV}`)
    console.log(`🚀 Server name : ${process.env.APP_NAME}`)
    console.log(`🚀 Server ready at ${serverInfo.url}`);
    console.log(`🚀 =====================================`)
});