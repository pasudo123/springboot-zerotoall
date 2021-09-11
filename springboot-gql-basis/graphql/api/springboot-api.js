const { RESTDataSource } = require('apollo-datasource-rest')

class SpringBootAPI extends RESTDataSource {

    constructor() {
        // 무조건 super 호출
        super()
        // 해당 api 에 알맞는 baseURL 설정
        this.baseURL = `${process.env.SPRING_BOOT_API_PATH}`
    }

    willSendRequest(request) {
        request.headers.set('my-state', 'study-springboot-gql')
    }

    async fetchItems() {
        console.info('fetchItems Query')
        return await this.get(`items`)

    }

    async fetchItemById(id) {
        console.info('fetchItemById Query')
        const response = await this.get(`items/${id}`)
        return response.content
    }

    async fetchItemTags() {
        const response = await this.get(`items-tags`)
        return response.content
    }

    async fetchItemTagById(id) {
        const response = await this.get(`items-tags/${id}`)
        return response.content
    }
}

module.exports = SpringBootAPI