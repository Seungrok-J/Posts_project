import axios, { AxiosResponse } from 'axios';

const api = axios.create({
	baseURL: 'http://localhost:8080/api',
	withCredentials: true,
	headers: {
		'Content-Type': 'application/json'
	}
});

interface LoginResponse {
	userId: string;
	userName: string;
	nickName: string;
	userEmail: string;
	role: string;
	sessionId: string;
}

export const login = async (email: string, password: string): Promise<LoginResponse> => {
	const response: AxiosResponse<LoginResponse> = await api.post('/auth/login', { userEmail: email, password });
	return response.data;
};

export const logout = async (): Promise<void> => {
	await api.post('/auth/logout');
};

export const getPublicKey = async (): Promise<string> => {
	const response: AxiosResponse<string> = await api.get('/auth/public-key');
	return response.data;
};