import {gql} from "apollo-server-core";

export const noticeTypeDefs = gql`
    type Query {
        fetchNotices: [Notice!]!
        fetchNoticeById(id: ID!): Notice
    }

    type Mutation {
        updateVoteById(id: ID!): Int!
    }

    "공지사항"
    type Notice {
        id: ID! @cacheControl(maxAge: 240)
        title: String! @cacheControl(maxAge: 0)
        contents: String! @cacheControl(maxAge: 0)
        votes: Int! @cacheControl(maxAge: 240)
    }
`