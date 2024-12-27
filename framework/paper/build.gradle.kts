plugins {
    id("plutoproject.paper-conventions")
}

dependencies {
    api(projects.frameworkPaperApi)
    api(project(":framework:shared"))
}
