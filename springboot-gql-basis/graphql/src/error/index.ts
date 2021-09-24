import {ApolloError} from "apollo-server-core";

// https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/defineProperty

export class CustomBadRequestError extends ApolloError {
    constructor(message: string, extensions?: Record<string, any>) {
        super(message, 'B001', extensions)
        Object.defineProperty(this, 'name', {
            value: 'CustomBadRequestError',
            writable: false
        });
    }
}