dependencies {
    implementation(project(":rewards-common"))

    compileOnly(libs.spigot)

    implementation(libs.sql.provider)
    implementation(libs.fast.util)
    implementation(libs.inventory.framework)
    implementation(libs.configuration)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}