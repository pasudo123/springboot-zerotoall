import {CustomBadRequestError} from "../error";

export const resolvers = {
    Query: {
        hello: () => 'world !!!',
        fetchItems: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItems()
        },
        fetchItemById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [아이템] 을 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            return context.dataSources.springbootAPI.fetchItemById(args.id)
        },
        fetchItemTags: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItemTags()
        },
        fetchItemTagById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [아이템] 을 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            return context.dataSources.springbootAPI.fetchItemTagById(args.id)
        },
        fetchItemWithItemTagById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [아이템] 과 [아이템태그] 를 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            // merge
            const item = await context.dataSources.springbootAPI.fetchItemById(args.id)
            const itemTags = await context.dataSources.springbootAPI.fetchItemTagsByItemId(args.id)

            let itemWithTags: { item: {}, itemTags: [] } = { item: null, itemTags: []}
            itemWithTags.item = item
            itemWithTags.itemTags = itemTags

            return itemWithTags
        }
    },
}

module.exports = resolvers