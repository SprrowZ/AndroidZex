package com.freedom.gradle

import java.util.jar.JarEntry
import java.util.jar.JarFile

class RouterMappingCollector {
    private static final String PACKAGE_NAME = "com/dawn/come/mapping"
    private static final String FILE_NAME_PREFIX = "RouterMapping_"
    private static final String FILE_NAME_SUFFIX = ".class"
    private final Set<String> mappingClassNames = new HashSet<>()

     Set<String> getMappingClassName() {
        return mappingClassNames
    }
    /**
     * 收集class文件或者class文件目录中的映射表类
     * @param classFile
     */
    void collect(File classFile) {
        if (classFile == null || !classFile.exists()) {
            return
        }
        if (classFile.isFile()) {
            if (classFile.absolutePath.contains(PACKAGE_NAME)
                    && classFile.name.startsWith(FILE_NAME_PREFIX)
                    && classFile.name.endsWith(FILE_NAME_SUFFIX)) {
                String className = classFile.name.replace(FILE_NAME_SUFFIX, "")
                mappingClassNames.add(className)
            }
        } else {
            classFile.listFiles().each { file ->
                collect(file)
            }
        }
    }
    /**
     * 收集jar包中的映射表类
     * @param jarFile
     */
    void collectFromJarFile(File jarFile) {
        Enumeration<JarEntry> enumeration = new JarFile(jarFile).entries()
        if (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            String entryName = jarEntry.name
            if (entryName.contains(PACKAGE_NAME)
                    && entryName.contains(FILE_NAME_PREFIX)
                    && entryName.contains(FILE_NAME_SUFFIX)) {
                String className = entryName.replace(PACKAGE_NAME, "")
                        .replace("/", "")
                        .replace(FILE_NAME_SUFFIX, "")
                mappingClassNames.add(className)
            }
        }
    }
}