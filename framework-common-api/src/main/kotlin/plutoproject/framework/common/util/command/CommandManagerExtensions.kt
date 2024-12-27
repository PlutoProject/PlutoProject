package plutoproject.framework.common.util.command

import org.incendo.cloud.CommandManager
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.annotations.parser.MethodArgumentParser
import org.incendo.cloud.kotlin.coroutines.annotations.installCoroutineSupport
import org.incendo.cloud.parser.ArgumentParser
import plutoproject.framework.common.util.jvm.findClass

@Suppress("UNCHECKED_CAST", "UnusedReceiverParameter")
fun <C, T> CommandManager<C>.getKotlinMethodArgumentParserClass(): Class<ArgumentParser<C, T>> =
    (findClass("org.incendo.cloud.kotlin.coroutines.annotations.KotlinMethodArgumentParser")
        ?: error("Env error: cannot find KotlinMethodArgumentParser class")) as Class<ArgumentParser<C, T>>

@Suppress("UNCHECKED_CAST", "UnusedReceiverParameter")
fun <C, T> CommandManager<C>.getMethodArgumentParserClass(): Class<ArgumentParser<C, T>> =
    MethodArgumentParser::class.java as Class<ArgumentParser<C, T>>

inline val <reified C> CommandManager<C>.annotationParser: AnnotationParser<C>
    get() = AnnotationParser(this, C::class.java).installCoroutineSupport()
