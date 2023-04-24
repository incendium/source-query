plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    ignoreFailures = false

    config = files("${rootDir}/.detekt/config.yml")
    buildUponDefaultConfig = true

    baseline = file("${rootDir}/.detekt/baseline.xml")
}
