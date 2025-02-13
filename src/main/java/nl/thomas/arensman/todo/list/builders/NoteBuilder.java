package nl.thomas.arensman.todo.list.builders;

import nl.thomas.arensman.todo.list.models.Note;

import java.time.LocalDateTime;

public class NoteBuilder {
    private int noteId;
    private String noteValue;
    private LocalDateTime noteCreationTime;

    public NoteBuilder setNoteId(int noteId) {
        this.noteId = noteId;
        return this;
    }

    public NoteBuilder setNoteValue(String noteValue) {
        this.noteValue = noteValue;
        return this;
    }

    public NoteBuilder setNoteCreationTime(LocalDateTime noteCreationTime) {
        this.noteCreationTime = noteCreationTime;
        return this;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNoteValue() {
        return noteValue;
    }

    public LocalDateTime getNoteCreationTime() {
        return noteCreationTime;
    }

    public Note build() {
        return new Note(this);
    }
}
