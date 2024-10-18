import React, {ChangeEvent, FormEvent} from "react";

export interface FormData {
	userName: string;
	nickName: string;
	userEmail: string;
	password: string;
	confirmPassword: string;
	role: string;
	verificationCode: string;
}

export interface User {
	userId: string;
	userName: string;
	nickName: string;
	userEmail: string;
	sessionId: string;
	role: string;
}

export interface LoginFormProps {
	email: string;
	setEmail: (email: string) => void;
	password: string;
	setPassword: (password: string) => void;
	handleSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
	error: string;
}

export interface LoginFormData {
	email: string;
	password: string;
}

export interface LoginResponse extends User {}

export interface SignUpFormProps {
	formData: FormData;
	handleChange: (e: ChangeEvent<HTMLInputElement>) => void;
	handleSubmit: (e: FormEvent<HTMLFormElement>) => void;
	handleEmailVerification: () => Promise<void>;
	verifyEmailToken: (authCode: string) => Promise<void>;
	checkNicknameAvailability: () => Promise<void>;
	message: string;
}

export interface FormData {
	userName: string;
	nickName: string;
	userEmail: string;
	password: string;
	confirmPassword: string;
	role: string;
	verificationCode: string;
}