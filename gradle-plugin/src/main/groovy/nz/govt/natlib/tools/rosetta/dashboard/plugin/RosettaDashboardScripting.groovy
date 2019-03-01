package nz.govt.natlib.tools.rosetta.dashboard.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Rosetta dashboard scripting plugin.
 */
class RosettaDashboardScripting implements Plugin<Project> {
    Project project

    /**
     * Apply this plugin to the given target object.
     * Currently we do nothing, but it does make its dependencies available.
     *
     * @param target The target object
     */
    @Override
    void apply(Project target) {
        this.project = target
    }
}
