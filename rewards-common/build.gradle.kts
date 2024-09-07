dependencies {
    compileOnly(libs.spigot)

    compileOnly(libs.annotations)
    compileOnly(libs.fast.util)

    compileOnly(libs.sql.provider)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}