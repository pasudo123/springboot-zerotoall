import {RequestOptions, RESTDataSource} from "apollo-datasource-rest";

export class SpringBootAPI extends RESTDataSource {
    baseURL = `${process.env.SPRING_BOOT_API_PATH}`

    willSendRequest(request: RequestOptions) {
        /**
         * api 요청 이전에 인터셉터로 이용해서 헤더에 특정한 값을 넣거나 할 수 있음
         */
        const today = new Date()
        const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
        request.headers.set('state', `study-springboot-gql : ${time}`)
    }

    async fetchItems() {
        return this.get(`items`)

    }

    async fetchItemById(id: number) {
        return this.get(`items/${id}`)
    }

    async fetchItemTagsByItemId(id: number) {
        return this.get(`items/${id}/tags`)
    }

    async fetchItemTags() {
        return this.get(`item-tags`)
    }

    async fetchItemTagById(id: number) {
        return this.get(`item-tags/${id}`)
    }
}

module.exports = SpringBootAPI