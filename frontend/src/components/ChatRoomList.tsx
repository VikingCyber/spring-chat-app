import { useState } from "react";
import type { ChatRoom } from "../types/chat";

type Props = {
    rooms: ChatRoom[];
    selectedRoomId: number | null
    onSelectRoom: (id: number) => void
    onCreateRoom: (name: string) => void
};

export default function ChatRoomList({ rooms, selectedRoomId, onSelectRoom, onCreateRoom }: Props) {
    const [newRoomName, setNewRoomName] = useState("");

    return (
        <div style={{ width: '250px', borderRight: '1px solid #ccc' }}>
            <h3 style={{ padding: '10px' }}>Комнаты</h3>
            {rooms.map(room => (
            <div
                key={room.id}
                onClick={() => onSelectRoom(room.id)}
                style={{
                    padding: '10px',
                    cursor: 'pointer',
                    backgroundColor: room.id === selectedRoomId ? '#eee' : 'transparent'
                }}
            >
                {room.name}
        </div>
      ))}
        <div style={{marginTop: "10px"}}>
            <input
                value={newRoomName}
                onChange={e => setNewRoomName(e.target.value)}
                placeholder="Название комнаты"
                style={{ width: "100%", marginBottom: "5px"}}
            />
            <button
                onClick={() => {
                    if (newRoomName.trim()) {
                        onCreateRoom(newRoomName);
                        setNewRoomName("");
                    }
                }}
                style={{ width: "100%" }}
            >
                Создать комнату
            </button>
        </div>
    </div>
    )
}