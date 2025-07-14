export type ChatRoom = {
    id: number;
    name: string;
    description?: string;
    avatarUrl?: string;
    createdAt: string;
};

export type ChatMessage = {
    messageId: number;
    roomId: number;
    sender: string;
    content: string;
    sentAt: string;
};