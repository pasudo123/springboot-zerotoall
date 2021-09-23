const { RESTDataSource, RequestOptions } = require('apollo-datasource-rest')

class SpringBootAPI extends RESTDataSource {
    baseURL = `${process.env.SPRING_BOOT_API_PATH}`

    willSendRequest(request) {
        const today = new Date()
        const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
        request.headers.set('state', `study-springboot-gql : ${time}`)
    }

    async fetchItems() {
        return await this.get(`items`)

    }

    async fetchItemById(id) {
        return await this.get(`items/${id}`)
    }

    async fetchItemTags() {
        return await this.get(`item-tags`)
    }

    async fetchItemTagById(id) {
        return await this.get(`item-tags/${id}`)
    }
}

module.exports = SpringBootAPI