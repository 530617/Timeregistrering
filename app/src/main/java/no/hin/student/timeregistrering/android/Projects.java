package no.hin.student.timeregistrering.android;

import java.util.ArrayList;

import no.hin.student.timeregistrering.applikasjon.Project;

/**
 * Created by kurt-erik on 03.03.2015.
 */
public class Projects {
    private static ArrayList<Project> projects = new ArrayList<Project>();

    public static void addProject(Project project) {
        projects.add(project);
    }

    public static Project getProject(String id) {
        return null;
    }

    public static ArrayList<Project> getAllProjects() {
        return projects;
    }
}
