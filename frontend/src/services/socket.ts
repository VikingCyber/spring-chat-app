import SockJs from 'sockjs-client';
import { Client } from '@stomp/stompjs';


export const createStompClient = (token: string) => {
    const client = new Client({
        brokerURL: undefined,
        webSocketFactory: () => new SockJs('http://localhost:8080/ws-chat'),
        connectHeaders: {
            Authorization: `Bearer ${token}`
        },
        reconnectDelay: 5000
    });
    return client;
}