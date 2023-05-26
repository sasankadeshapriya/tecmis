package notice;

public class Notice {
    private String title;
    private String content;
    private String attachmentUri;

    public Notice(String title, String content, String attachmentUri) {
        this.title = title;
        this.content = content;
        this.attachmentUri = attachmentUri;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAttachmentUri() {
        return attachmentUri;
    }
}
