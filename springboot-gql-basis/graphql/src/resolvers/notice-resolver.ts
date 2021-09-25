import {CustomBadRequestError} from "../error";

export const noticeResolver = {
    Query: {
        fetchNotices: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.noticeAPI.fetchNotices()
        },
        fetchNoticeById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [공지사항] 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            // info.cacheControl.setCacheHint({maxAge: 60})
            return context.dataSources.noticeAPI.fetchNoticeById(args.id)
        },
    },
    Mutation: {
        updateVoteById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [공지사항] 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            return context.dataSources.noticeAPI.updateVoteById(args.id)
        }
    }
}