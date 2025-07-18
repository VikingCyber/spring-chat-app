import { useEffect, useState } from "react";
import type { ChatMessage } from "../types/chat";
import { useAutoScroll } from "../hooks/useAutoScroll";
import { ChevronDownIcon } from "@heroicons/react/24/solid";
import MessageBubble from "./MessageBubble";


type Props = {
  messages: ChatMessage[];
  onSendMessage: (text: string) => void;
};

export default function ChatWindow({ messages, onSendMessage }: Props) {
  const {
    containerRef,
    endRef,
    isAtBottom,
    handleScroll,
    scrollToBottom,
  } = useAutoScroll<HTMLDivElement>();

  const [input, setInput] = useState("");
  const currentUsername = localStorage.getItem("username") ?? ""; // или через контекст

  useEffect(() => {
    if (isAtBottom) scrollToBottom();
  }, [messages]);

  const handleSend = () => {
    if (input.trim()) {
      onSendMessage(input.trim());
      setInput("");
    }
  };

  return (
    <div className="flex flex-col flex-grow relative">
      <div
        ref={containerRef}
        onScroll={handleScroll}
        className="flex-1 overflow-y-auto p-4 space-y-1 bg-gray-50 px-20"
      >
        {messages.map((m) => (
          <MessageBubble key={m.messageId} message={m} currentUsername={currentUsername} />
        ))}
        <div ref={endRef} />
      </div>

      {!isAtBottom && (
        <button
          onClick={scrollToBottom}
          className="absolute bottom-20 right-4 bg-blue-500 text-white p-3 rounded-full shadow hover:bg-blue-600 transition cursor-pointer"
        >
          <ChevronDownIcon className="w-5 h-5"/>
        </button>
      )}

      <div className="p-3 border-t bg-white flex gap-2 px-40">
        <input
          className="flex-grow border border-gray-300 rounded-full px-4 py-2 focus:outline-none"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSend()}
          placeholder="Введите сообщение"
        />
        <button
          onClick={handleSend}
          className="bg-blue-500 text-white rounded-full px-4 py-2 hover:bg-blue-600 cursor-pointer"
        >
          Отправить
        </button>
      </div>
    </div>
  );
}
