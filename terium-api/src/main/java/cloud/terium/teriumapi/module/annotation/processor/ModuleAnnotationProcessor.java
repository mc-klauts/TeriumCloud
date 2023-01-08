package cloud.terium.teriumapi.module.annotation.processor;

import cloud.terium.teriumapi.module.annotation.Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.util.*;
import java.util.Set;

@SupportedAnnotationTypes({"cloud.terium.teriumapi.module.annotation.Module"})
public class ModuleAnnotationProcessor extends AbstractProcessor {

    private ProcessingEnvironment environment;
    private String pluginClassFound;
    private boolean hasPrintedPluginError;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.environment = processingEnv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Module.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated with "
                                + Module.class.getCanonicalName());
                return false;
            }

            Name qualifiedName = ((TypeElement) element).getQualifiedName();

            if (Objects.equals(pluginClassFound, qualifiedName.toString())) {
                if (!hasPrintedPluginError) {
                    environment.getMessager()
                            .printMessage(Diagnostic.Kind.WARNING, "Spigot does not support "
                                    + "multiple plugins in one jar. We will generate a plugin.yml using " + pluginClassFound
                                    + " for your plugin's main class.");
                    hasPrintedPluginError = true;
                }
                return false;
            }

            Module module = element.getAnnotation(Module.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", module.name());
            jsonObject.addProperty("author", module.author());
            jsonObject.addProperty("version", module.version());
            jsonObject.addProperty("description", module.description());
            jsonObject.addProperty("main-class", qualifiedName.toString());
            jsonObject.addProperty("reloadable", module.reloadable());
            jsonObject.addProperty("type", module.moduleType().toString());

            try {
                FileObject object = environment.getFiler()
                        .createResource(StandardLocation.CLASS_OUTPUT, "", "terium-info.json");
                try (Writer writer = new BufferedWriter(object.openWriter())) {
                    new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(jsonObject, new Gson().newJsonWriter(writer));
                }
            } catch (IOException e) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Unable to generate plugin file");
            }
        }

        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
