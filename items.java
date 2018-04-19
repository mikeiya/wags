package wags.gravatar;

public class items {
    private String GravatarUrl;
    private String Username;
    private String Badges;

    public items(String gravatarUrl, String username, String badges) {
        GravatarUrl = gravatarUrl;
        Username = username;
        Badges = badges;
    }

    public String getGravatarUrl() {
        return GravatarUrl;
    }

    public String getUsername() {
        return Username;
    }

    public String getBadges() {
        return Badges;
    }
}
