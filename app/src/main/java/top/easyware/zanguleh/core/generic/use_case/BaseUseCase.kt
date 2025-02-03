package top.easyware.zanguleh.core.generic.use_case

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}