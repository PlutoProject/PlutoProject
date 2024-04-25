package ink.pmc.common.bedrockadaptive.velocity.replacements

import ink.pmc.common.bedrockadaptive.delegations.BedrockSerializerDelegation
import ink.pmc.common.bedrockadaptive.utils.messageTranslatorClass
import ink.pmc.common.utils.bedrock.bedrockFormats
import ink.pmc.common.utils.jvm.byteBuddy
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.method.MethodDescription
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy
import net.bytebuddy.matcher.ElementMatchers.named
import net.bytebuddy.matcher.ElementMatchers.takesArguments
import net.kyori.adventure.text.serializer.legacy.CharacterAndFormat
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

private val formats = run {
    val list = mutableListOf<CharacterAndFormat>()
    list.addAll(CharacterAndFormat.defaults())
    list.addAll(bedrockFormats)
    list
}
val bedrockSerializer = LegacyComponentSerializer.legacySection().toBuilder()
    .formats(formats)
    .build()

object BedrockColorSerializerReplacement {

    fun init() {
        byteBuddy
            .redefine(messageTranslatorClass)
            .visit(
                Advice.to(BedrockSerializerDelegation.CovertMessage::class.java)
                    .on(named<MethodDescription>("convertMessage").and(takesArguments(2)))
            )
            .visit(
                Advice.to(BedrockSerializerDelegation.ConvertToJavaMessage::class.java)
                    .on(named("convertToJavaMessage"))
            )
            .make()
            .load(messageTranslatorClass.classLoader, ClassReloadingStrategy.fromInstalledAgent())
    }

}