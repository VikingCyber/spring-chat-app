import { useEffect, useState } from "react";
import api from "../api/api";
import type { ChatRoom, ChatMessage } from "../types/chat";
import ChatRoomList from "../components/ChatRoomList";
import ChatWindow from "../components/ChatWindow";
import { useStompClient } from "../hooks/useStompClient";

export default function ChatPage() {
  const [rooms, setRooms] = useState<ChatRoom[]>([]);
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const { client, isConnected} = useStompClient("ws://localhost:8080/ws-chat");

  useEffect(() => {
    api.get("chat").then(res => setRooms(res.data.data))
  }, []);

  useEffect(() => {
    if (!client || !isConnected || !selectedRoomId) return;

    api.get(`chat/${selectedRoomId}/messages`).then(res => setMessages(res.data.data));

    const subscription = client.subscribe(`/topic/chat/${selectedRoomId}`, (message) => {
      const newMessage: ChatMessage = JSON.parse(message.body);
      setMessages(prev => [...prev, newMessage]);
    });

    return () => {
      subscription.unsubscribe();
    };
  }, [client, isConnected, selectedRoomId]);

  const handleSendMessage = (text: string) => {
    if (!client || !isConnected || !selectedRoomId) return;

    client.publish({
      destination: "/app/chat.sendMessage",
      body: JSON.stringify({
        roomId: selectedRoomId,
        content: text,
      }),
    });
  };

  const handleCreateRoom = (name: string, typeId: number = 1) => {
    api.post("chat", { name, typeId })
        .then(res => {
            setRooms(prev => [...prev, res.data.data]);
        })
        .catch(err => {
            alert("Ошибка создания комнаты: " + (err.response?.data?.message || err.message));
        });
  };

  return (
    <div className="flex h-screen overflow-hidden">
      <ChatRoomList
        rooms={rooms}
        selectedRoomId={selectedRoomId}
        onSelectRoom={setSelectedRoomId}
        onCreateRoom={handleCreateRoom}
      />
      {selectedRoomId && (
        <ChatWindow
          messages={messages}
          onSendMessage={handleSendMessage}
        />
      )}
    </div>
  );
}
