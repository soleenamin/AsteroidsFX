package dk.sdu.cbse.core;

import java.lang.module.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Path pluginsDir = Paths.get("plugins");
            ModuleFinder finder = ModuleFinder.of(pluginsDir);

            List<String> moduleNames = finder.findAll().stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            Configuration config = ModuleLayer.boot()
                    .configuration()
                    .resolve(finder, ModuleFinder.of(), moduleNames);

            ModuleLayer layer = ModuleLayer.boot()
                    .defineModulesWithOneLoader(config, ClassLoader.getSystemClassLoader());


            App.main(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
