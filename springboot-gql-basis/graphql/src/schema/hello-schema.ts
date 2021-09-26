import {gql} from "apollo-server-core";

// The GraphQL schema
// type Query 는 데이터를 들고오기 위해서 필수 : https://www.apollographql.com/docs/tutorial/schema/#the-query-type
// type Mutation 은 데이터를 수정하기 위해서 필수 : https://www.apollographql.com/docs/tutorial/schema/#the-mutation-type
export const helloTypeDefs = gql`

    # https://www.apollographql.com/docs/apollo-server/schema/creating-directives/
    enum CacheControlScope {
        PUBLIC
        PRIVATE
    }

    directive @cacheControl(
        maxAge: Int
        scope: CacheControlScope
        inheritMaxAge: Boolean
    ) on FIELD_DEFINITION | OBJECT | INTERFACE | UNION

    type Query {
        hello: String
        me: User
    }

    type User @key(fields: "id") {
        id: ID!
        username: String
    }
`