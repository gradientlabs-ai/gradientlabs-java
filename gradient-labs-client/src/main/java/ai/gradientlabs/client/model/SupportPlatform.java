package ai.gradientlabs.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A customer support platform.
 */
public enum SupportPlatform {
    /**
     * Dixa.
     */
    DIXA("dixa"),

    /**
     * Freshchat.
     */
    FRESHCHAT("freshchat"),

    /**
     * Freshdesk.
     */
    FRESHDESK("freshdesk"),

    /**
     * Gmail.
     */
    GMAIL("gmail"),

    /**
     * Intercom.
     */
    INTERCOM("intercom"),

    /**
     * Kustomer.
     */
    KUSTOMER("kustomer"),

    /**
     * LiveChat.
     */
    LIVECHAT("livechat"),

    /**
     * The Gradient Labs public API.
     */
    PUBLIC_API("public-api"),

    /**
     * The Gradient Labs chat SDK.
     */
    CHAT_SDK("chat-sdk"),

    /**
     * Salesforce.
     */
    SALESFORCE("salesforce"),

    /**
     * Zendesk.
     */
    ZENDESK("zendesk"),

    /**
     * LiveKit.
     */
    LIVEKIT("livekit"),

    /**
     * Twilio.
     */
    TWILIO("twilio"),

    /**
     * Talkdesk.
     */
    TALKDESK("talkdesk"),

    /**
     * Intercom voice.
     */
    INTERCOM_VOICE("intercom-voice");

    private final String value;

    SupportPlatform(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
