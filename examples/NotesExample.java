import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.Note;
import ai.gradientlabs.client.model.NoteStatus;
import ai.gradientlabs.client.request.CreateNoteRequest;
import ai.gradientlabs.client.request.SetNoteStatusRequest;
import ai.gradientlabs.client.request.UpdateNoteRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Example demonstrating how to use the Notes API.
 */
public class NotesExample {

    public static void main(String[] args) {
        // Create client
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .build();

        // Create a note with body content
        CreateNoteRequest createRequest = CreateNoteRequest.builder()
                .id("note-001")
                .title("Important Product Update")
                .body("We are launching a new feature next week. Please inform customers about the enhanced dashboard.")
                .authorId("author-123")
                .startTime(Instant.now())
                .endTime(Instant.now().plus(30, ChronoUnit.DAYS))
                .build();

        Note note = client.createNote(createRequest);
        System.out.println("Created note: " + note);

        // Create a note with webpage URL instead of body
        CreateNoteRequest webpageNoteRequest = CreateNoteRequest.builder()
                .id("note-002")
                .title("Product Documentation")
                .webpageUrl("https://docs.example.com/product-guide")
                .authorId("author-123")
                .build();

        Note webpageNote = client.createNote(webpageNoteRequest);
        System.out.println("Created webpage note: " + webpageNote);

        // Update a note
        UpdateNoteRequest updateRequest = UpdateNoteRequest.builder()
                .title("Updated: Important Product Update")
                .body("We are launching TWO new features next week. Please inform customers about the enhanced dashboard and analytics.")
                .authorId("author-456")
                .build();

        Note updatedNote = client.updateNote("note-001", updateRequest);
        System.out.println("Updated note: " + updatedNote);

        // Set note status to live
        SetNoteStatusRequest statusRequest = new SetNoteStatusRequest(NoteStatus.LIVE);
        client.setNoteStatus("note-001", statusRequest);
        System.out.println("Note status set to LIVE");

        // Later, delete the note
        client.deleteNote("note-001");
        System.out.println("Note deleted");
    }
}
