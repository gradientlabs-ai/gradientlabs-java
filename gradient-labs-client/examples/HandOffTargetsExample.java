import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.Channel;
import ai.gradientlabs.client.model.HandOffTarget;
import ai.gradientlabs.client.request.DeleteHandOffTargetRequest;
import ai.gradientlabs.client.request.SetDefaultHandOffTargetRequest;
import ai.gradientlabs.client.request.UpsertHandOffTargetRequest;

import java.util.List;

/**
 * Example demonstrating how to use the Hand-Off Targets API.
 * <p>
 * Hand-off targets define where conversations can be transferred when they need
 * human intervention or routing to other systems.
 * <p>
 * <strong>Note:</strong> All hand-off target operations require a Management API key.
 */
public class HandOffTargetsExample {

    public static void main(String[] args) {
        // Create client with Management API key
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .build();

        // Create or update a hand-off target
        UpsertHandOffTargetRequest upsertRequest = UpsertHandOffTargetRequest.builder()
                .id("accounts-team")
                .name("Accounts Team")
                .build();

        client.upsertHandOffTarget(upsertRequest);
        System.out.println("Created/updated hand-off target: accounts-team");

        // Create another hand-off target
        UpsertHandOffTargetRequest supportRequest = UpsertHandOffTargetRequest.builder()
                .id("support-team")
                .name("Customer Support Team")
                .build();

        client.upsertHandOffTarget(supportRequest);
        System.out.println("Created/updated hand-off target: support-team");

        // Create a billing team target
        UpsertHandOffTargetRequest billingRequest = UpsertHandOffTargetRequest.builder()
                .id("billing-team")
                .name("Billing Team")
                .build();

        client.upsertHandOffTarget(billingRequest);
        System.out.println("Created/updated hand-off target: billing-team");

        // List all hand-off targets
        List<HandOffTarget> targets = client.listHandOffTargets();
        System.out.println("\nAll hand-off targets:");
        for (HandOffTarget target : targets) {
            System.out.println("  - " + target.getId() + ": " + target.getName());
        }

        // Set default hand-off target for chat channel
        SetDefaultHandOffTargetRequest defaultRequest = SetDefaultHandOffTargetRequest.builder()
                .id("support-team")
                .channel(Channel.CHAT)
                .build();

        client.setDefaultHandOffTarget(defaultRequest);
        System.out.println("\nSet default hand-off target for CHAT channel to: support-team");

        // Set default hand-off target for email channel
        SetDefaultHandOffTargetRequest emailDefaultRequest = SetDefaultHandOffTargetRequest.builder()
                .id("accounts-team")
                .channel(Channel.EMAIL)
                .build();

        client.setDefaultHandOffTarget(emailDefaultRequest);
        System.out.println("Set default hand-off target for EMAIL channel to: accounts-team");

        // Clear default hand-off target for a channel (set to empty string)
        SetDefaultHandOffTargetRequest clearDefaultRequest = SetDefaultHandOffTargetRequest.builder()
                .id("")
                .channel(Channel.CHAT)
                .build();

        client.setDefaultHandOffTarget(clearDefaultRequest);
        System.out.println("\nCleared default hand-off target for CHAT channel");

        // Delete a hand-off target
        // Note: This will fail if the target is in use (in a procedure or intent)
        DeleteHandOffTargetRequest deleteRequest = DeleteHandOffTargetRequest.builder()
                .id("billing-team")
                .build();

        try {
            client.deleteHandOffTarget(deleteRequest);
            System.out.println("\nDeleted hand-off target: billing-team");
        } catch (Exception e) {
            System.err.println("Failed to delete hand-off target (may be in use): " + e.getMessage());
        }

        // List targets again to verify deletion
        targets = client.listHandOffTargets();
        System.out.println("\nRemaining hand-off targets:");
        for (HandOffTarget target : targets) {
            System.out.println("  - " + target.getId() + ": " + target.getName());
        }
    }
}
