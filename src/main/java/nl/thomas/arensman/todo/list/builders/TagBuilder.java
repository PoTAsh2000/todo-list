package nl.thomas.arensman.todo.list.builders;

import java.time.LocalDateTime;

public class TagBuilder {
    private int tagId;
    private String tagName;
    private String tagHexColor;
    private LocalDateTime tagCreationDate;

    public TagBuilder setTagId(int tagId) {
        this.tagId = tagId;
        return this;
    }

    public TagBuilder setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public TagBuilder setTagHexColor(String tagHexColor) {
        this.tagHexColor = tagHexColor;
        return this;
    }

    public TagBuilder setTagCreationDate(LocalDateTime tagCreationDate) {
        this.tagCreationDate = tagCreationDate;
        return this;
    }

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagHexColor() {
        return tagHexColor;
    }

    public LocalDateTime getTagCreationDate() {
        return tagCreationDate;
    }
}
