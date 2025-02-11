package top.easyware.core.model

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}