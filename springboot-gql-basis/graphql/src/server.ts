import {ApolloServer, ServerInfo} from "apollo-server";
import {ApolloServerPluginCacheControl, ApolloServerPluginLandingPageGraphQLPlayground} from "apollo-server-core";
import {formatterError} from "./error/formatter";
import {NoticeAPI} from "./api/notice";
import {ItemTagAPI} from "./api/item-tag";
import {ItemAPI} from "./api/item";
import {typeDefs} from "./schema";
import {merge} from 'lodash'
import {itemResolver} from "./resolvers/item-resolver";
import {itemTagResolver} from "./resolvers/item-tag-resolver";
import {noticeResolver} from "./resolvers/notice-resolver";
import {helloResolver} from "./resolvers/hello-resolver";


// dotenv 가 .env.{profile} 내 데이터를 읽게해준다.
require("dotenv").config({path: `.env.${process.env.NODE_ENV}`});

const server = new ApolloServer({
    typeDefs,
    resolvers: merge(itemResolver, itemTagResolver, noticeResolver, helloResolver),
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
        ApolloServerPluginCacheControl({
            // 1ms == 1000sec
            defaultMaxAge: 5000,
            // response 헤더에 cache-control 을 보내지 않는다.
            // calculateHttpHeaders: false
        })
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