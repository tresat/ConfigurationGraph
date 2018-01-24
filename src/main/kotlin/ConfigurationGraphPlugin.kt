import org.gradle.api.Plugin
import org.gradle.api.Project

open class ConfigurationGraphPluginExtension {
    companion object {
        const val DEFAULT_OUTPUT_FILE_NAME = "configuration_graph.txt"
    }

    var outputFileName = DEFAULT_OUTPUT_FILE_NAME
}

class ConfigurationGraphPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            extensions.create("configurationGraph", ConfigurationGraphPluginExtension::class.java)

            afterEvaluate {
                val config = extensions.findByType(ConfigurationGraphPluginExtension::class.java)
                val outputFileName = config?.outputFileName ?: ConfigurationGraphPluginExtension.DEFAULT_OUTPUT_FILE_NAME

                val outputFile = file(outputFileName).apply { createNewFile() }
                configurations.forEach { c ->
                    outputFile.writeText(c.name)
                }
            }
        }
    }
}
