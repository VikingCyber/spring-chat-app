import { useEffect, useState } from "react";
import api from "../api/api";
import type { ChatRoom, ChatMessage } from "../types/chat";
import ChatRoomList from "../components/ChatRoomList";
import ChatWindow from "../components/ChatWindow";
import { Client } from "@stomp/stompjs";

export default function ChatPage() {
  const [rooms, setRooms] = useState<ChatRoom[]>([]);
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [client, setClient] = useState<Client | null>(null);
  const [isConnected, setIsConnected] = useState(false);

  // Загружаем список комнат при старте
  useEffect(() => {
    api.get("chat").then(res => setRooms(res.data.data));
  }, []);

  // Инициализация STOMP клиента
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const stompClient = new Client({
      brokerURL: "ws://localhost:8080/ws-chat",
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("Connected to WebSocket");
        setIsConnected(true);
      },
      onStompError: (frame) => {
        console.error("Broker error", frame);
      },
    });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      stompClient.deactivate();
    };
  }, []);

  // Подписка на сообщения выбранной комнаты
  useEffect(() => {
    if (!client || !isConnected || !selectedRoomId) return;

    // Загружаем историю сообщений
    api.get(`chat/${selectedRoomId}/messages`).then(res => setMessages(res.data.data));

    // Подписка на сообщения
    const subscription = client.subscribe(`/topic/chat/${selectedRoomId}`, (message) => {
      const newMessage: ChatMessage = JSON.parse(message.body);
      setMessages(prev => [...prev, newMessage]);
    });

    // Отписка при смене комнаты или размонтировании
    return () => {
      subscription.unsubscribe();
    };
  }, [client, isConnected, selectedRoomId]);

  // Отправка сообщения
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
    <div style={{ display: "flex", height: "100vh" }}>
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
