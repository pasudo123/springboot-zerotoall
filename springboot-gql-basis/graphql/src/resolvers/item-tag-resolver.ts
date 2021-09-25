import {CustomBadRequestError} from "../error";

export const itemTagResolver = {
    Query: {
        fetchItemTags: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.itemTagAPI.fetchItemTags()
        },
        fetchItemTagById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] E로 [아이템] 을 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            return context.dataSources.itemTagAPI.fetchItemTagById(args.id)
        },
    }
}