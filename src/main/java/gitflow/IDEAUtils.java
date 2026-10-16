package gitflow;

import com.intellij.dvcs.DvcsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import git4idea.GitUtil;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class IDEAUtils {

    /**
     * Resolves the Git repository the user is currently working with.
     * <p>
     * This is a non-deprecated replacement for the historically used
     * {@code GitBranchUtil.getCurrentRepository(Project)}: it looks the repository
     * up for the currently selected file and falls back to the only repository of
     * the project.
     */
    @Nullable
    public static GitRepository getCurrentRepository(@NotNull Project project) {
        GitRepositoryManager manager = GitUtil.getRepositoryManager(project);

        VirtualFile selectedFile = DvcsUtil.getSelectedFile(project);
        if (selectedFile != null) {
            GitRepository repository = manager.getRepositoryForFile(selectedFile);
            if (repository != null) {
                return repository;
            }
        }

        List<GitRepository> repositories = manager.getRepositories();
        return repositories.size() == 1 ? repositories.get(0) : null;
    }

    public static Project getActiveProject(){
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }
        return activeProject;
    }
}
