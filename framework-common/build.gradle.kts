plugins {
    id("plutoproject.common-conventions")
}

dependencies {
    api(projects.frameworkCommonApi)
    api(projects.frameworkProto)
    compileOnly(libs.ksp.api)
}
