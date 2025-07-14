import type { ChatMessage } from "../types/chat";

type Props = {
  messages: ChatMessage[];
  onSendMessage: (text: string) => void;
};

export default function ChatWindow({ messages, onSendMessage }: Props) {
  return (
    <div style={{ flex: 1, padding: '10px' }}>
      <div style={{ height: '400px', overflowY: 'auto' }}>
        {messages.map(m => (
          <div key={m.messageId}>
            <b>{m.sender}</b>: {m.content}
          </div>
        ))}
      </div>
      <input
        placeholder="Введите сообщение"
        onKeyDown={e => {
          if (e.key === 'Enter') {
            onSendMessage((e.target as HTMLInputElement).value);
            (e.target as HTMLInputElement).value = '';
          }
        }}
        style={{ width: '100%', marginTop: '10px' }}
      />
    </div>
  );
}
