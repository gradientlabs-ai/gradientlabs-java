import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.Tool;
import ai.gradientlabs.client.model.ToolExecuteResult;
import ai.gradientlabs.client.request.ToolExecuteRequest;

import java.util.List;

/**
 * Example demonstrating tool execution.
 * <p>
 * Note: This requires a Management API key.
 */
public class ToolExecuteExample {

    public static void main(String[] args) {
        // Create client with local development settings
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_LOCAL_MGMT_KEY"))
                .baseUrl("http://127.0.0.1:4000")
                .build();

        try {
            // List all tools
            System.out.println("Fetching tools...");
            List<Tool> tools = client.listTools();

            // Find and execute the "Launch" tool
            for (Tool tool : tools) {
                if ("Launch".equals(tool.getName())) {
                    System.out.println("Executing tool: " + tool.getName());

                    // Create execution request with arguments
                    ToolExecuteRequest request = new ToolExecuteRequest()
                            .addArgument("speed", "slow");

                    // Execute the tool
                    ToolExecuteResult result = client.executeTool(tool.getId(), request);

                    // Check if execution was successful
                    if (result.isSuccess()) {
                        System.out.println("✓ Tool executed successfully");
                        System.out.println("  Result: " + result.getResult());
                    } else {
                        System.out.println("✗ Tool execution failed");
                        System.out.println("  Error: " + result.getError());
                    }
                }
            }

            System.out.println("Done");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
