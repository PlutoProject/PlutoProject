package plutoproject.framework.common.util.command

import org.incendo.cloud.CommandManager
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.kotlin.coroutines.annotations.installCoroutineSupport

inline val <reified C> CommandManager<C>.annotationParser: AnnotationParser<C>
    get() = AnnotationParser(this, C::class.java).installCoroutineSupport()