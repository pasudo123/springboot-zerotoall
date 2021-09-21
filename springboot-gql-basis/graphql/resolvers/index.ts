
const resolvers = {
    Query: {
        hello: () => 'world !!!',
        fetchItems: async (_, __, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItems()
        },
        fetchItem: async(_, { id }, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItemById(id)
        },
        fetchItemTags: async(_, __, {dataSources}) => {
            return await dataSources.springbootAPI.fetchItemTags()
        }
    },
}

module.exports = resolvers