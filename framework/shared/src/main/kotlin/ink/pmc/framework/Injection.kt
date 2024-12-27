package ink.pmc.framework

import com.mongodb.kotlin.client.coroutine.MongoCollection
import org.koin.core.qualifier.named
import org.koin.dsl.module
import plutoproject.framework.common.api.provider.Provider

val FRAMEWORK_CONFIG = named("framework_config")

private inline fun <reified T : Any> getCollection(name: String): MongoCollection<T> {
    return Provider.defaultMongoDatabase.getCollection(name)
}

val commonModule = module {

}
