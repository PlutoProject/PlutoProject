plugins {
    id("plutoproject.common-conventions")
}

dependencies {
    compileOnly(libs.velocity)
    with(extensions.getByType<PlutoDependencyHandlerExtension>()) {
        downloadIfRequired(libs.cloud.velocity)
        api(libs.bundles.mccoroutine.velocity)
    }
}