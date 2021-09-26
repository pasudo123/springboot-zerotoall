import {gql} from "apollo-server-core";

export const itemTagTypeDefs = gql`
    type Query {
        fetchItemTags: [ItemTag!]!
        fetchItemTagById(id: ID!): ItemTag
    }

    "아이템 태그"
    type ItemTag {
        "아이디"
        id: ID!
        "아이템 태그 명칭"
        name: String!
    }
`