plugins {
    id("plutoproject.velocity-conventions")
}

dependencies {
    api(projects.frameworkVelocityApi)
    api(project(":framework:shared"))
}
