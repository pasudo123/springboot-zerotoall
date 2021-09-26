export const helloResolver = {
    Query: {
        hello: () => 'world !!!',
        me: () => ({ id: "1", username: "@ava" })
    }
}