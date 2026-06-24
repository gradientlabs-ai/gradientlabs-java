import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.BackOfficeTask;
import ai.gradientlabs.client.request.CreateBackOfficeTaskRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Example demonstrating how to create back office tasks.
 * <p>
 * A back office task is dispatched to an AI agent for processing. Address the task with an
 * agent group id (prefixed {@code agent_}) together with the procedure id (prefixed
 * {@code proc_}) within that agent to start from.
 * <p>
 * <strong>Note:</strong> Back office task operations require a Public API key.
 */
public class BackOfficeTaskExample {

    public static void main(String[] args) {
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .build();

        // ==================== Create a Back Office Task ====================

        System.out.println("=== Creating a Back Office Task ===");

        Map<String, Object> input = new HashMap<>();
        input.put("customer_id", "cust_123");
        input.put("refund_amount", 4999);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("source", "public-api-example");

        CreateBackOfficeTaskRequest request = CreateBackOfficeTaskRequest.builder()
                .id("refund-2024-0001")
                .agentId("agent_01ham6bzcdeja9xzqhjf6daq30")
                .procedureId("proc_01ham6bzcdeja9xzqhjf6daq31")
                .input(input)
                .metadata(metadata)
                .addAttachment(CreateBackOfficeTaskRequest.Attachment.fromUrl(
                        "refund-policy.pdf",
                        "https://help.example.com/articles/refund-policy"))
                .build();

        BackOfficeTask task = client.createBackOfficeTask(request);

        System.out.println("Created task: " + task.getId());
        System.out.println("Agent: " + task.getAgentId());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Created: " + task.getCreated());

        // ==================== Read a Back Office Task ====================

        System.out.println("\n=== Reading the Back Office Task ===");

        BackOfficeTask read = client.readBackOfficeTask(task.getId());
        System.out.println("Status: " + read.getStatus());
        if (read.getResult() != null) {
            System.out.println("Result type: " + read.getResult().getResultType());
        }

        System.out.println("\n=== All back office task operations completed successfully! ===");
    }
}
