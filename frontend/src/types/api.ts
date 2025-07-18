export interface ApiResponse<T> {
    status: number;
    message: string;
    data: T;
    timestamp: string;
}

export interface JwtResponse {
    token: string;
    username: string;
}

export interface UserResponse {
    id: number;
    username: string;
    email: string;
}