export const resolvers = {
    Query: {
        hello: () => 'world !!!',
        fetchItems: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItems()
        },
        fetchItemById: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItemById(args.id)
        },
        fetchItemTags: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItemTags()
        },
        fetchItemTagById: async (parent: any, args: any, context: any, info: any) => {
            return context.dataSources.springbootAPI.fetchItemTagById(args.id)
        },
        fetchItemWithItemTagById: async (parent: any, args: any, context: any, info: any) => {
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