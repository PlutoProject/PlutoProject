package plutoproject.framework.common.api.feature.annotation

@Target()
@Retention(AnnotationRetention.RUNTIME)
annotation class Dependency(
    val id: String,
    val load: Load = Load.AFTER,
    val required: Boolean = true,
)
