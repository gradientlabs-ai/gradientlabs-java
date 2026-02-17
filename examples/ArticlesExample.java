import ai.gradientlabs.client.GradientLabsClient;
import ai.gradientlabs.client.model.PublicationStatus;
import ai.gradientlabs.client.model.UsageStatus;
import ai.gradientlabs.client.model.Visibility;
import ai.gradientlabs.client.request.SetArticleUsageStatusRequest;
import ai.gradientlabs.client.request.UpsertArticleRequest;
import ai.gradientlabs.client.request.UpsertArticleTopicRequest;

import java.time.Instant;

/**
 * Example demonstrating how to use the Articles API.
 * <p>
 * Articles are documents that the AI agent can work with to answer customer questions.
 * Topics enable you to categorize your help articles into groups.
 * <p>
 * <strong>Note:</strong> All article operations require an Integration API key.
 */
public class ArticlesExample {

    public static void main(String[] args) {
        // Create client with Integration API key
        GradientLabsClient client = GradientLabsClient.builder()
                .apiKey(System.getenv("GLABS_API_KEY"))
                .build();

        // Create a top-level topic
        UpsertArticleTopicRequest topicRequest = UpsertArticleTopicRequest.builder()
                .id("topic-1")
                .name("Account Management")
                .description("How to manage your account in our amazing app!")
                .visibility(Visibility.PUBLIC)
                .status(PublicationStatus.PUBLISHED)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .addData("category", "onboarding")
                .addData("priority", 1)
                .build();

        client.upsertArticleTopic(topicRequest);
        System.out.println("Created topic: topic-1");

        // Create a sub-topic
        UpsertArticleTopicRequest subTopicRequest = UpsertArticleTopicRequest.builder()
                .id("topic-1a")
                .parentId("topic-1")
                .name("Personal details")
                .description("How to change your personal details")
                .visibility(Visibility.PUBLIC)
                .status(PublicationStatus.PUBLISHED)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .build();

        client.upsertArticleTopic(subTopicRequest);
        System.out.println("Created sub-topic: topic-1a");

        // Create a published article
        UpsertArticleRequest articleRequest = UpsertArticleRequest.builder()
                .id("article-1")
                .authorId("neal@gradient-labs.ai")
                .title("Change my address")
                .description("Learn how to update your address")
                .body("Go to the settings screen in the app, and then tap 'update my address.'")
                .visibility(Visibility.PUBLIC)
                .topicId("topic-1a")
                .status(PublicationStatus.PUBLISHED)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .addData("reading_time", "2 minutes")
                .addData("tags", new String[]{"settings", "address"})
                .build();

        client.upsertArticle(articleRequest);
        System.out.println("Created article: article-1");

        // Create a draft article (not published yet)
        UpsertArticleRequest draftRequest = UpsertArticleRequest.builder()
                .id("article-2")
                .authorId("support@gradient-labs.ai")
                .title("Reset my password")
                .description("Step-by-step guide to reset your password")
                .body("Click 'Forgot Password' on the login screen...")
                .visibility(Visibility.PUBLIC)
                .topicId("topic-1a")
                .status(PublicationStatus.DRAFT)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .build();

        client.upsertArticle(draftRequest);
        System.out.println("Created draft article: article-2");

        // Create an internal article (only for employees)
        UpsertArticleRequest internalRequest = UpsertArticleRequest.builder()
                .id("article-3")
                .authorId("admin@gradient-labs.ai")
                .title("Internal Escalation Process")
                .description("How to escalate complex customer issues")
                .body("For complex issues that require engineering involvement...")
                .visibility(Visibility.INTERNAL)
                .topicId("topic-1")
                .status(PublicationStatus.PUBLISHED)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .build();

        client.upsertArticle(internalRequest);
        System.out.println("Created internal article: article-3");

        // Set article usage status to make it available to the AI agent
        SetArticleUsageStatusRequest enableRequest = new SetArticleUsageStatusRequest(UsageStatus.ON);
        client.setArticleUsageStatus("article-1", enableRequest);
        System.out.println("\nEnabled article-1 for AI agent use");

        // Disable an article from being used by the AI agent
        SetArticleUsageStatusRequest disableRequest = new SetArticleUsageStatusRequest(UsageStatus.OFF);
        client.setArticleUsageStatus("article-2", disableRequest);
        System.out.println("Disabled article-2 from AI agent use");

        // Update an existing article
        UpsertArticleRequest updateRequest = UpsertArticleRequest.builder()
                .id("article-1")
                .authorId("support@gradient-labs.ai")
                .title("Change my address - Updated")
                .description("Learn how to update your address in your account settings")
                .body("Go to the settings screen in the app, and then tap 'update my address.' You can change your billing and shipping addresses separately.")
                .visibility(Visibility.PUBLIC)
                .topicId("topic-1a")
                .status(PublicationStatus.PUBLISHED)
                .created(Instant.now())
                .lastEdited(Instant.now())
                .addData("reading_time", "3 minutes")
                .addData("tags", new String[]{"settings", "address", "billing"})
                .build();

        client.upsertArticle(updateRequest);
        System.out.println("\nUpdated article: article-1");

        // Delete an article
        client.deleteArticle("article-3");
        System.out.println("Deleted article: article-3");

        System.out.println("\nAll article operations completed successfully!");
    }
}
