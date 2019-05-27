package com.github.unchama.contextualexecutor.builder

import arrow.core.Left
import arrow.core.None
import arrow.core.Right
import arrow.core.Some
import com.github.unchama.contextualexecutor.builder.response.asResponseToSender

/**
 * [ContextualExecutorBuilder.argumentsParser]が要求する,
 * 引数文字列から[ResponseOrResult<Any>]への関数の作成を行うためのスコープオブジェクト.
 *
 * [ArgumentParserScope.ScopeProvider.parser]を通してスコープ付き関数をそのような関数に変換できる.
 */
object ArgumentParserScope {

    /**
     * メッセージなしで「失敗」を表す[ResponseOrResult]を作成する.
     */
    fun failWithoutError(): ResponseOrResult<Nothing> = Left(None)

    /**
     * メッセージ付きの「失敗」を表す[ResponseOrResult]を作成する.
     */
    fun failWith(message: String): ResponseOrResult<Nothing> = Left(Some(message.asResponseToSender()))

    /**
     * メッセージ付きの「失敗」を表す[ResponseOrResult]を作成する.
     */
    fun failWith(message: List<String>): ResponseOrResult<Any> = Left(Some(message.asResponseToSender()))

    /**
     * [result]により「成功」したことを示す[ResponseOrResult]を作成する.
     */
    fun succeedWith(result: Any): ResponseOrResult<Any> = Right(result)

    object ScopeProvider {
        /**
         * [ArgumentParserScope]のスコープ付き関数をプレーンな関数へと変換する.
         */
        fun parser(function: ArgumentParserScope.(String) -> ResponseOrResult<Any>): (String) -> ResponseOrResult<Any> =
                { argument -> ArgumentParserScope.function(argument) }
    }
}
