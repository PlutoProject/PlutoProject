package ink.pmc.options

import ink.pmc.options.api.EntryValueType.*
import ink.pmc.options.api.OptionDescriptor
import ink.pmc.options.api.OptionEntry
import ink.pmc.options.api.OptionsContainer
import ink.pmc.options.api.OptionsManager
import ink.pmc.options.repositories.OptionsContainerRepository
import ink.pmc.utils.concurrent.submitAsync
import ink.pmc.utils.json.toObject
import ink.pmc.utils.multiplaform.player.PlayerWrapper
import kotlinx.coroutines.delay
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializerOrNull
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.minutes

abstract class BaseOptionsManagerImpl : OptionsManager, KoinComponent {
    private var isClosed = false
    private val repo by inject<OptionsContainerRepository>()
    private val registeredDescriptors = mutableMapOf<String, OptionDescriptor<*>>()
    private val loadedContainers = ConcurrentHashMap<UUID, OptionsContainer>()

    init {
        submitAsync {
            while (!isClosed) {
                delay(10.minutes)
                loadedContainers.entries.removeIf {
                    !isPlayerOnline(it.key)
                }
            }
        }
    }

    abstract fun isPlayerOnline(uuid: UUID): Boolean

    override fun isContainerLoaded(uuid: UUID): Boolean {
        return loadedContainers.containsKey(uuid)
    }

    override fun unloadContainer(uuid: UUID) {
        loadedContainers.remove(uuid)
    }

    override suspend fun createContainer(uuid: UUID): OptionsContainer {
        check(getContainer(uuid) == null) { "OptionContainer for $uuid already existed" }
        return OptionsContainerImpl(uuid, mapOf()).also {
            save(it)
        }
    }

    override suspend fun createContainer(player: PlayerWrapper<*>): OptionsContainer {
        return createContainer(player.uuid)
    }

    @OptIn(InternalSerializationApi::class)
    @Suppress("UNCHECKED_CAST")
    override suspend fun getContainer(uuid: UUID): OptionsContainer? {
        val model = repo.findById(uuid) ?: return null
        val entriesMap = mutableMapOf<String, OptionEntry<*>>()
        model.entries.forEach {
            val descriptor = getDescriptor(it.key)
            if (descriptor == null) {
                logger.warning("Descriptor not found for ${it.key}")
                return@forEach
            }
            val entry = when (descriptor.type) {
                INT -> OptionEntryImpl(descriptor as OptionDescriptor<Int>, Json.decodeFromString(it.value))
                LONG -> OptionEntryImpl(descriptor as OptionDescriptor<Long>, Json.decodeFromString(it.value))
                SHORT -> OptionEntryImpl(descriptor as OptionDescriptor<Short>, Json.decodeFromString(it.value))
                BYTE -> OptionEntryImpl(descriptor as OptionDescriptor<Byte>, Json.decodeFromString(it.value))
                DOUBLE -> OptionEntryImpl(descriptor as OptionDescriptor<Double>, Json.decodeFromString(it.value))
                FLOAT -> OptionEntryImpl(descriptor as OptionDescriptor<Float>, Json.decodeFromString(it.value))
                BOOLEAN -> OptionEntryImpl(descriptor as OptionDescriptor<Boolean>, Json.decodeFromString(it.value))
                STRING -> OptionEntryImpl(descriptor as OptionDescriptor<String>, Json.decodeFromString(it.value))
                OBJECT -> {
                    val objClass =
                        checkNotNull(descriptor.objectClass) { "Object class cannot be null: ${descriptor.key}" }
                    val kSerializer = objClass.kotlin.serializerOrNull()
                    if (kSerializer != null) {
                        OptionEntryImpl(
                            descriptor as OptionDescriptor<Any>,
                            Json.decodeFromString(kSerializer, it.value)
                        )
                    } else {
                        OptionEntryImpl(descriptor as OptionDescriptor<Any>, it.value.toObject(objClass))
                    }
                }
            }
            entriesMap[it.key] = entry
        }
        return OptionsContainerImpl(uuid, entriesMap)
    }

    override suspend fun getContainer(player: PlayerWrapper<*>): OptionsContainer? {
        return getContainer(player.uuid)
    }

    override suspend fun getContainerOrCreate(uuid: UUID): OptionsContainer {
        return getContainer(uuid) ?: createContainer(uuid)
    }

    override suspend fun getContainerOrCreate(player: PlayerWrapper<*>): OptionsContainer {
        return getContainerOrCreate(player.uuid)
    }

    override suspend fun deleteContainer(uuid: UUID) {
        unloadContainer(uuid)
        repo.deleteById(uuid)
    }

    override suspend fun deleteContainer(player: PlayerWrapper<*>) {
        return deleteContainer(player.uuid)
    }

    override suspend fun save(container: OptionsContainer) {
        container.save()
    }

    override fun registerOptionDescriptor(descriptor: OptionDescriptor<*>) {
        require(!registeredDescriptors.containsKey(descriptor.key)) { "Descriptor for ${descriptor.key} already registered" }
        registeredDescriptors[descriptor.key] = descriptor
    }

    private fun getDescriptor(key: String): OptionDescriptor<*>? {
        return registeredDescriptors[key]
    }

    override fun close() {
        check(!isClosed) { "OptionsManager already closed" }
        isClosed = true
    }
}