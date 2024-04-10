package effectivekotlin

interface OrderService {
    fun ordered(): Boolean
}

class SnackOrderService : OrderService {
    override fun ordered(): Boolean {
        throw UnsupportedOperationException("SnackOrderService 는 지원하지 않습니다.")
    }
}

class CarOrderService : OrderService {
    override fun ordered(): Boolean {
        return true
    }
}
