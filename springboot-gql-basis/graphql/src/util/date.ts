import {DateTime} from "luxon";

export function toYYYYMMddHHmmssFormat(date: Date) {
    return DateTime.fromJSDate(date).toFormat("yyyy-LL-dd HH:mm:ss")
}