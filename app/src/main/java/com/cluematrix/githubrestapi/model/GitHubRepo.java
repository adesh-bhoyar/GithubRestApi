package com.cluematrix.githubrestapi.model;

import java.io.Serializable;

public class GitHubRepo implements Serializable {
    private String name;
    private String description;
    private String html_url;

    public GitHubRepo(String name, String description, String html_url) {
        this.name = name;
        this.description = description;
        this.html_url = html_url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHtml_url() {
        return html_url;
    }
}

