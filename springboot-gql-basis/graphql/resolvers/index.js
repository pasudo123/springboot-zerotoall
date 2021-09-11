
const resolvers = {
    Query: {
        hello: () => 'world !!!',
        fetchItems: async (_, __, {dataSources}) => {
            const response = await dataSources.springbootAPI.fetchItems()
            return response
        },
    },
}

module.exports = resolvers