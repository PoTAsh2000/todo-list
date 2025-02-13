package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.TagBuilder;

import java.time.LocalDateTime;

public class Tag {
    private final int tagId;
    private final String tagName;
    private final String tagHexColor;
    private final LocalDateTime tagCreationDate;

    public Tag(TagBuilder tagBuilder) {
        this.tagId = tagBuilder.getTagId();
        this.tagName = tagBuilder.getTagName();
        this.tagHexColor = tagBuilder.getTagHexColor();
        this.tagCreationDate = tagBuilder.getTagCreationDate();
    }

    public TagBuilder getTagBuilder () {
        return new TagBuilder();
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
