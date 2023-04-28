package com.DSGS.service;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class GitService {

    private static final String REPO_URL = "https://github.com/HeshamAlzoubi/SwaProjectTest.git";
    private static final String LOCAL_DIR = System.getProperty("java.io.tmpdir");

    public String cloneRepo(String fileName) throws GitAPIException, IOException {
        String name = StringUtils.getFilename(REPO_URL).substring(0, StringUtils.getFilename(REPO_URL).indexOf("."));
        String local = LOCAL_DIR + File.separator + UUID.randomUUID();
        File localDir = new File(local);

        if (localDir.exists()) {
            FileUtils.deleteDirectory(localDir);
        }

        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(REPO_URL)
                .setDirectory(new File(local))
                .setCloneAllBranches(true)
                .setCloneSubmodules(true);

        try (Git git = cloneCommand.call()) {
            return local;
        }
    }

}
