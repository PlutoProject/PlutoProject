subprojects {
    dependencies {
        compileOnly(project(":utils:api"))
        compileOnly(project(":member:api"))
    }
}