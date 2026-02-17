import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.ListProcedureVersionsResponse;
import ai.gradientlabs.client.ProcedureListResponse;
import ai.gradientlabs.client.model.PaginationInfo;
import ai.gradientlabs.client.model.Procedure;
import ai.gradientlabs.client.model.ProcedureStatus;
import ai.gradientlabs.client.model.ProcedureVersion;
import ai.gradientlabs.client.request.ListProceduresRequest;
import ai.gradientlabs.client.request.SetProcedureExperimentVersionRequest;
import ai.gradientlabs.client.request.SetProcedureLimitRequest;

import java.util.List;

/**
 * Example demonstrating how to use the Procedures API.
 * <p>
 * Procedures are instructions that the AI agent uses to resolve specific customer problems.
 * You can manage procedure versions, set experiments for gradual rollouts, and configure
 * daily usage limits.
 * <p>
 * <strong>Note:</strong> All procedure operations require a Management API key.
 */
public class ProceduresExample {

    public static void main(String[] args) {
        // Create client with Management API key
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .build();

        // ==================== List Procedures ====================

        System.out.println("=== Listing All Procedures ===");

        // List all procedures
        ListProceduresRequest listRequest = ListProceduresRequest.empty();
        ProcedureListResponse response = client.listProcedures(listRequest);

        System.out.println("Found " + response.getProcedures().size() + " procedures");
        for (Procedure proc : response.getProcedures()) {
            System.out.println("- " + proc.getName() + " (" + proc.getId() + "): " + proc.getStatus());
        }

        // Check for pagination
        PaginationInfo pagination = response.getPagination();
        if (pagination != null && pagination.getNext() != null) {
            System.out.println("\nMore results available. Use cursor: " + pagination.getNext());

            // Get next page
            ListProceduresRequest nextPageRequest = ListProceduresRequest.builder()
                    .cursor(pagination.getNext())
                    .build();
            ProcedureListResponse nextPage = client.listProcedures(nextPageRequest);
            System.out.println("Next page has " + nextPage.getProcedures().size() + " procedures");
        }

        // ==================== Filter by Status ====================

        System.out.println("\n=== Listing Live Procedures ===");

        // List only live procedures
        ListProceduresRequest liveRequest = ListProceduresRequest.builder()
                .status(ProcedureStatus.LIVE)
                .build();
        ProcedureListResponse liveResponse = client.listProcedures(liveRequest);

        System.out.println("Found " + liveResponse.getProcedures().size() + " live procedures");
        for (Procedure proc : liveResponse.getProcedures()) {
            System.out.println("- " + proc.getName() + " (created: " + proc.getCreated() + ")");
        }

        // ==================== Read a Specific Procedure ====================

        if (!response.getProcedures().isEmpty()) {
            Procedure firstProc = response.getProcedures().get(0);
            String procedureId = firstProc.getId();

            System.out.println("\n=== Reading Procedure: " + procedureId + " ===");

            Procedure proc = client.readProcedure(procedureId);
            System.out.println("ID: " + proc.getId());
            System.out.println("Name: " + proc.getName());
            System.out.println("Description: " + proc.getDescription());
            System.out.println("Status: " + proc.getStatus());
            System.out.println("Author: " + (proc.getAuthor() != null ? proc.getAuthor().getEmail() : "N/A"));
            System.out.println("Created: " + proc.getCreated());
            System.out.println("Updated: " + proc.getUpdated());
            System.out.println("Has Daily Limit: " + proc.isDailyLimited());
            if (proc.isDailyLimited()) {
                System.out.println("Max Daily Conversations: " + proc.getMaxDailyConversations());
            }

            // ==================== Set Procedure Limit ====================

            System.out.println("\n=== Setting Daily Limit ===");

            // Enable daily limit with maximum of 500 conversations per day
            SetProcedureLimitRequest limitRequest = SetProcedureLimitRequest.builder()
                    .hasDailyLimit(true)
                    .maxDailyConversations(500)
                    .build();

            Procedure updatedProc = client.setProcedureLimit(procedureId, limitRequest);
            System.out.println("Updated procedure: " + updatedProc.getName());
            System.out.println("Has Daily Limit: " + updatedProc.isDailyLimited());
            System.out.println("Max Daily Conversations: " + updatedProc.getMaxDailyConversations());

            // Remove daily limit
            SetProcedureLimitRequest removeLimitRequest = SetProcedureLimitRequest.builder()
                    .hasDailyLimit(false)
                    .build();

            Procedure unlimitedProc = client.setProcedureLimit(procedureId, removeLimitRequest);
            System.out.println("\nRemoved daily limit");
            System.out.println("Has Daily Limit: " + unlimitedProc.isDailyLimited());

            // ==================== List Procedure Versions ====================

            System.out.println("\n=== Listing Procedure Versions ===");

            ListProcedureVersionsResponse versionsResponse = client.listProcedureVersions(procedureId);
            List<ProcedureVersion> versions = versionsResponse.getVersions();

            System.out.println("Found " + versions.size() + " versions");
            for (ProcedureVersion version : versions) {
                System.out.println("\nVersion " + version.getVersion() + ":");
                System.out.println("  Name: " + version.getName());
                System.out.println("  Author: " + version.getAuthor());
                System.out.println("  Created: " + version.getCreated());
                System.out.println("  Live: " + version.isLive());
                System.out.println("  Experimental: " + version.isExperimental());
                if (version.isExperimental() && version.getExperimentalConfig() != null) {
                    System.out.println("  Max Daily Conversations: " +
                            version.getExperimentalConfig().getMaxDailyConversations());
                }
            }

            // ==================== Version Management ====================

            if (versions.size() >= 2) {
                // Get a non-live version to use as example
                ProcedureVersion testVersion = versions.stream()
                        .filter(v -> !v.isLive())
                        .findFirst()
                        .orElse(versions.get(versions.size() - 1));

                int versionNumber = testVersion.getVersion();

                System.out.println("\n=== Setting Experimental Version ===");

                // Set as experimental version with daily limit of 100 conversations
                SetProcedureExperimentVersionRequest experimentRequest = SetProcedureExperimentVersionRequest.builder()
                        .maxDailyConversations(100)
                        .replace(true)  // Replace any existing experiment
                        .build();

                client.setProcedureExperimentVersion(procedureId, versionNumber, experimentRequest);
                System.out.println("Set version " + versionNumber + " as experimental with 100 daily conversations");

                // Verify the change
                ListProcedureVersionsResponse updatedVersions = client.listProcedureVersions(procedureId);
                ProcedureVersion experimentalVersion = updatedVersions.getVersions().stream()
                        .filter(ProcedureVersion::isExperimental)
                        .findFirst()
                        .orElse(null);

                if (experimentalVersion != null) {
                    System.out.println("Confirmed: Version " + experimentalVersion.getVersion() +
                            " is now experimental");
                }

                // Unset experimental version
                System.out.println("\n=== Unsetting Experimental Version ===");
                client.unsetProcedureExperimentVersion(procedureId, versionNumber);
                System.out.println("Unset experimental version " + versionNumber);

                // Set as live version
                System.out.println("\n=== Setting Live Version ===");
                client.setProcedureLiveVersion(procedureId, versionNumber);
                System.out.println("Set version " + versionNumber + " as live");

                // Verify the change
                ListProcedureVersionsResponse liveVersions = client.listProcedureVersions(procedureId);
                ProcedureVersion liveVersion = liveVersions.getVersions().stream()
                        .filter(ProcedureVersion::isLive)
                        .findFirst()
                        .orElse(null);

                if (liveVersion != null) {
                    System.out.println("Confirmed: Version " + liveVersion.getVersion() + " is now live");
                }

                // Unset live version (if needed)
                System.out.println("\n=== Unsetting Live Version ===");
                client.unsetProcedureLiveVersion(procedureId, versionNumber);
                System.out.println("Unset live version " + versionNumber);
            } else {
                System.out.println("\nNot enough versions to demonstrate version management");
                System.out.println("(Need at least 2 versions to safely test version operations)");
            }
        } else {
            System.out.println("\nNo procedures found. Create some procedures first!");
        }

        System.out.println("\n=== All procedure operations completed successfully! ===");
    }
}
