package br.com.schumaker.force.framework.run;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The ClassScanner class provides utility methods for scanning classes within a specified package.
 * It can retrieve classes with a specific annotation and all classes within a package.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ClassScanner {

    /**
     * Retrieves the classes within the specified package that are annotated with the specified annotation.
     *
     * @param packageName the name of the package to scan.
     * @param annotation the annotation to look for.
     * @return a list of classes annotated with the specified annotation.
     * @throws Exception if an error occurs during class scanning.
     */
    public static List<Class<?>> getClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation) throws Exception {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        List<Class<?>> classes = getClasses(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                annotatedClasses.add(clazz);
            }
        }

        return annotatedClasses;
    }

    /**
     * Retrieves all classes within the specified package.
     *
     * @param packageName the name of the package to scan.
     * @return a list of classes within the specified package.
     * @throws Exception if an error occurs during class scanning.
     */
    public static List<Class<?>> getClasses(String packageName) throws Exception{
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);

            if (filePath.startsWith("file:") && filePath.contains("!")) {
                String jarPath = filePath.substring("file:".length(), filePath.indexOf("!"));
                classes.addAll(getClassesFromJarFile(jarPath, path));
            } else {
                classes.addAll(findClasses(new File(filePath), packageName));
            }
        }

        return classes;
    }

    /**
     * Retrieves classes from a JAR file within the specified package path.
     *
     * @param jarFilePath the path to the JAR file.
     * @param packagePath the package path to scan within the JAR file.
     * @return a list of classes within the specified package path in the JAR file.
     * @throws Exception if an error occurs during class scanning.
     */
    private static List<Class<?>> getClassesFromJarFile(String jarFilePath, String packagePath) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);

        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();

            if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                String className = entryName.replace('/', '.').substring(0, entryName.length() - ".class".length());
                classes.add(Class.forName(className));
            }
        }

        jarFile.close();
        return classes;
    }

    /**
     * Finds classes within the specified directory and package name.
     *
     * @param directory the directory to scan.
     * @param packageName the package name to scan.
     * @return a list of classes within the specified directory and package name.
     * @throws Exception if an error occurs during class scanning.
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - ".class".length());
                    classes.add(Class.forName(className));
                }
            }
        }

        return classes;
    }
}
