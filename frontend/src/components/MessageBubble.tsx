import type { ChatMessage } from "../types/chat";
import { timeFormatter,  } from "../utils/dateFormatters";

type Props = {
  message: ChatMessage;
  currentUsername: string;
};

export default function MessageBubble({ message, currentUsername }: Props) {
  const isOwn = message.sender === currentUsername;
  const safeDate = new Date(Date.parse(message.createdAt));
  const sendTime = timeFormatter.format(safeDate);
  console.log("Raw message:", message);
  console.log(sendTime)

  return (
    <div className={`flex mb-2 ${isOwn ? "justify-end" : "justify-start"}`}>
      {!isOwn && (
        <div className="mr-2 w-8 h-8 mt-7 rounded-full bg-indigo-400 flex items-center justify-center text-xs font-bold">
          {message.sender.charAt(0).toUpperCase()}
        </div>
      )}

      <div
        className={`rounded-2xl py-1 px-3 max-w-xs md:max-w-md shadow 
        ${isOwn ? "bg-green-300  rounded-br-none" : "bg-gray-200 text-black rounded-bl-none"}`}
      >
        {!isOwn && (
            <div className="text-xs font-semibold text-gray-600 mb-1">
            {message.sender}
            </div>
        )}
        <div className="justify-between items-end gap-2 max-w-full"> 
            <div className="relative break-words whitespace-pre-wrap pr-8">
                {message.content}
            <span className="text-[0.625rem] text-gray-500 absolute bottom-0 right-0">
                {sendTime}
            </span>
            </div>

        </div>
      </div>
    </div>
  );
}
