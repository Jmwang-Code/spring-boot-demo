package juc.chat;

import java.util.concurrent.ConcurrentHashMap;

public class ChatApplication {
    // 存储用户的在线状态
    private ConcurrentHashMap<String, Boolean> onlineStatusMap;

    // 存储用户的消息记录
    private ConcurrentHashMap<String, StringBuilder> messageRecordsMap;

    public ChatApplication() {
        this.onlineStatusMap = new ConcurrentHashMap<>();
        this.messageRecordsMap = new ConcurrentHashMap<>();
    }

    // 用户登录
    public void userLogin(String username) {
        onlineStatusMap.put(username, true);
        messageRecordsMap.put(username, new StringBuilder());
        System.out.println(username + " logged in.");
    }

    // 用户退出登录
    public void userLogout(String username) {
        onlineStatusMap.remove(username);
        messageRecordsMap.remove(username);
        System.out.println(username + " logged out.");
    }

    // 发送消息
    public void sendMessage(String sender, String receiver, String message) {
        StringBuilder senderMessageRecord = messageRecordsMap.get(sender);
        StringBuilder receiverMessageRecord = messageRecordsMap.get(receiver);

        if (senderMessageRecord != null && receiverMessageRecord != null) {
            senderMessageRecord.append("You: ").append(message).append("\n");
            receiverMessageRecord.append(sender).append(": ").append(message).append("\n");
            System.out.println(sender + " sent message to " + receiver + ": " + message);
        } else {
            System.out.println("User not found or offline.");
        }
    }

    // 获取用户的消息记录
    public String getMessageRecord(String username) {
        StringBuilder messageRecord = messageRecordsMap.get(username);
        return messageRecord != null ? messageRecord.toString() : "No messages.";
    }

    // 获取用户的在线状态
    public boolean isUserOnline(String username) {
        return onlineStatusMap.getOrDefault(username, false);
    }

    public static void main(String[] args) {
        ChatApplication chatApp = new ChatApplication();

        // 模拟用户登录
        chatApp.userLogin("爱丽丝");
        chatApp.userLogin("鲍勃");

        // 发送消息
        chatApp.sendMessage("爱丽丝", "鲍勃", "Hello, 鲍勃!");
        chatApp.sendMessage("鲍勃", "爱丽丝", "Hi, 爱丽丝!");

        // 获取消息记录
        System.out.println("Alice's message record:");
        System.out.println(chatApp.getMessageRecord("爱丽丝"));

        System.out.println("Bob's message record:");
        System.out.println(chatApp.getMessageRecord("鲍勃"));

        // 模拟用户退出登录
        chatApp.userLogout("爱丽丝");
        chatApp.userLogout("鲍勃");
    }
}
