import {ApolloError} from "apollo-server-core";
import {toYYYYMMddHHmmssFormat} from "../util/date";

// 에러를 로그로 남기기 위한 방법.
export const formatterError = (error: ApolloError) => {
    console.error(``)
    console.error(`=============================================`)
    console.error(`=============== graphql error ===============`)
    console.error(`code :: ${error.extensions?.code}`)
    console.error(`path :: ${error.path}`)
    console.error(`time :: ${toYYYYMMddHHmmssFormat(new Date())}`)
    console.error(`message :: ${error.message}`)
    console.error(`original error : ${error.originalError}`)

    // 반드시 리턴해줄 것, 해주지 않는다면 클라이언트나 측으로 에러정보가 전달되지 않는다.
    return error
}