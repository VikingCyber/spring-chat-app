import { useState } from "react";
import type { ChatRoom } from "../types/chat";
import Navbar from "./Navbar";

type Props = {
  rooms: ChatRoom[];
  selectedRoomId: number | null;
  onSelectRoom: (id: number) => void;
  onCreateRoom: (name: string) => void;
};

export default function ChatRoomList({ rooms, selectedRoomId, onSelectRoom, onCreateRoom }: Props) {
  const [newRoomName, setNewRoomName] = useState("");

  return (
    <div className="w-64 border-r border-gray-300 p-4 flex flex-col bg-white overflow-y-auto h-full mt-2">
      <Navbar/>
      <h3 className="text-lg font-semibold mb-4">Комнаты</h3>
      <div className="flex-1 overflow-y-auto">
        {rooms.map((room) => (
          <div
            key={room.id}
            onClick={() => onSelectRoom(room.id)}
            className={`p-2 rounded cursor-pointer mb-1 hover:bg-gray-100 
              ${room.id === selectedRoomId ? "bg-gray-200 font-bold" : ""}`}
          >
            {room.name}
          </div>
        ))}
      </div>
      <div className="mt-4">
        <input
          className="w-full border border-gray-300 rounded px-2 py-1 mb-2"
          value={newRoomName}
          onChange={(e) => setNewRoomName(e.target.value)}
          placeholder="Название комнаты"
        />
        <button
          className="w-full bg-blue-500 text-white py-1 rounded hover:bg-blue-600 cursor-pointer"
          onClick={() => {
            if (newRoomName.trim()) {
              onCreateRoom(newRoomName);
              setNewRoomName("");
            }
          }}
        >
          Создать комнату
        </button>
      </div>
    </div>
  );
}
