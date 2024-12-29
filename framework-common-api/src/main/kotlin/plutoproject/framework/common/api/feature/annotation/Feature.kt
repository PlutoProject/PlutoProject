package plutoproject.framework.common.api.feature.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Feature(
    val id: String,
    val platform: Platform,
    val dependencies: Array<Dependency> = [],
)
