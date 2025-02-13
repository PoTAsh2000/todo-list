package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.NoteBuilder;

import java.time.LocalDateTime;

public class Note {
    private final int noteId;
    private final String noteValue;
    private final LocalDateTime noteCreationDate;

    public Note(NoteBuilder noteBuilder) {
        this.noteId = noteBuilder.getNoteId();
        this.noteValue = getNoteValue();
        this.noteCreationDate = getNoteCreationDate();
    }

    public static NoteBuilder getBuilder(){
        return new NoteBuilder();
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNoteValue() {
        return noteValue;
    }

    public LocalDateTime getNoteCreationDate() {
        return noteCreationDate;
    }
}
