plugins {
    idea
    versions
    alias(libs.plugins.versionCatalogUpdate)
}

allprojects {
    group = "com.iamincendium.source-query"
    version = "1.0-SNAPSHOT"
}

repositories {
    mavenCentral()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

versionCatalogUpdate {
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}
