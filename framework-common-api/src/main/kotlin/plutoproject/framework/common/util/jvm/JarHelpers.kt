package plutoproject.framework.common.util.jvm

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

fun extractFileFromJar(filePathInJar: String, outputPath: Path): File {
    val inputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(filePathInJar)
        ?: error("File not found in jar: $filePathInJar")
    val outputFile = outputPath.toFile()
    outputFile.parentFile?.mkdirs()
    inputStream.use { input ->
        Files.copy(input, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
    if (!outputFile.exists()) {
        error("Unable to extract file from jar (source: $filePathInJar, to: $outputPath)")
    }
    return outputFile
}

fun getResourceAsStreamFromJar(filePathInJar: String): InputStream? {
    return object {}.javaClass.classLoader.getResourceAsStream(filePathInJar)
}
