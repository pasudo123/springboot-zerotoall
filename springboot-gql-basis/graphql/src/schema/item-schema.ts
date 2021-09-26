import {gql} from "apollo-server-core";

export const itemTypeDefs = gql`
    type Query {
        fetchItems: [Item!]!
        fetchItemById(id: ID!): Item
        fetchItemWithItemTagById(id: ID!): ItemWithItemTag
    }

    "아이템"
    type Item {
        "아이디"
        id: ID!
        "아이템 명칭"
        name: String!
        "아이템 가격"
        price: String!
        "아이템 타입"
        type: ItemType!
    }

    "태그를 포함한 아이템"
    type ItemWithItemTag {
        item: Item
        itemTags: [ItemTag!]!
    }

    "아이템 타입"
    enum ItemType {
        FOOD
        LIFE
        FASHION
        CAR
    }
`