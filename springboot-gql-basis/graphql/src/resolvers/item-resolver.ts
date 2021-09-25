import {CustomBadRequestError} from "../error";


export const itemResolver = {
    Query: {
        fetchItems: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.itemAPI.fetchItems()
        },
        fetchItemById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [아이템] 을 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            return context.dataSources.itemAPI.fetchItemById(args.id)
        },
        fetchItemWithItemTagById: async (parent: any, args: any, context: any, info: any) => {
            if (args.id < 1) {
                throw new CustomBadRequestError(
                    `유효하지 않은 id [${args.id}] 로 [아이템] 과 [아이템태그] 를 조회하고 있습니다.`,
                    { argumentName: 'id' }
                )
            }

            // merge
            const item = await context.dataSources.itemAPI.fetchItemById(args.id)
            const itemTags = await context.dataSources.itemAPI.fetchItemTagsByItemId(args.id)

            let itemWithTags: { item: {}, itemTags: [] } = { item: null, itemTags: [] }
            itemWithTags.item = item
            itemWithTags.itemTags = itemTags

            return itemWithTags
        },
    }
}