const { gql } = require('apollo-server');

// The GraphQL schema
// type Query 는 데이터를 들고오기 위해서 필수 : https://www.apollographql.com/docs/tutorial/schema/#the-query-type
// type Mutation 은 데이터를 수정하기 위해서 필수 : https://www.apollographql.com/docs/tutorial/schema/#the-mutation-type
const typeDefs = gql`
    type Query {
        hello: String
        fetchItems: [Item!]!
        fetchItem(id: ID!): Item
        fetchItemTags: [ItemTag!]!
        fetchItemTag(id: ID!): ItemTag
    }
    
    "아이템"
    type Item {
        "아이디"
        id: ID!
        "아이템 명칭"
        name: String!
        "아이템 가격"
        price: String!
    }
    
    "아이템 태그"
    type ItemTag {
        "아이디"
        id: ID!
        "아이템 태그 명칭"
        name: String!
    }
    
`;

module.exports = typeDefs