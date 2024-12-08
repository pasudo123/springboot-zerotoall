//package benchmark
//
//import kotlinx.benchmark.Benchmark
//import kotlinx.benchmark.Scope
//import kotlinx.benchmark.Setup
//import org.openjdk.jmh.annotations.Fork
//import org.openjdk.jmh.annotations.Measurement
//import org.openjdk.jmh.annotations.State
//import org.openjdk.jmh.annotations.Warmup
//import java.util.concurrent.TimeUnit
//import kotlin.math.cos
//import kotlin.math.sqrt
//import kotlin.random.Random
//
//@State(Scope.Benchmark)
//@Fork(1)
//@Warmup(iterations = 5)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//class MyBenchmark {
//    private var data = 0.0
//
//    @Setup
//    fun setUp() {
//        data = Random.nextDouble()
//    }
//
//    @Benchmark
//    fun sqrtBenchmark(): Double {
//        return sqrt(data)
//    }
//
//    @Benchmark
//    fun cosBenchmark(): Double {
//        return cos(data)
//    }
//}