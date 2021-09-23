
const resolvers = {
    Query: {
        hello: () => 'world !!!',
        fetchItems: async (_, __, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItems()
        },
        fetchItemById: async(_, { id }, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItemById(id)
        },
        fetchItemTags: async(_, __, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItemTags()
        },
        fetchItemTagById: async(_, { id }, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItemTagById(id)
        },
        fetchItemWithItemTagById: async(_, { id }, {dataSources}) => {
            const item = dataSources.springbootAPI.fetchItemById(id)
            const itemTags = dataSources.springbootAPI.fetchItemTagById(id)
            return {
                item: null,
                itemTags: []
            }
        }
    },

}

module.exports = resolvers