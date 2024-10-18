import { useState, ChangeEvent, FormEvent } from 'react';
import { useNavigate } from "react-router-dom";
import api from '../api/api';
import { PATH } from '../constants/paths';
import { FormData } from '../@types/formTypes';

const useSignUp = () => {
	const [formData, setFormData] = useState<FormData>({
		userName: '',
		nickName: '',
		userEmail: '',
		password: '',
		confirmPassword: '',
		role: '',
		verificationCode: '',
	});

	const [emailVerified, setEmailVerified] = useState(false);
	const [nicknameAvailable, setNicknameAvailable] = useState(false);
	const [message, setMessage] = useState<string>('');
	const navigate = useNavigate();

	const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = e.target;
		setFormData(prevState => ({
			...prevState,
			[name]: value
		}));
	};

	const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (formData.password !== formData.confirmPassword) {
			setMessage('Passwords do not match!');
			return;
		}
		if (!emailVerified) {
			setMessage('Please verify your email.');
			return;
		}
		if (!nicknameAvailable) {
			setMessage('Nickname is taken, please choose another one.');
			return;
		}

		try {
			const response = await api.post('/auth/register', formData);
			if (response.status === 200) {
				setMessage('Registration successful!');
				setTimeout(() => {
					navigate(PATH.LOGIN);
				}, 1000);
			}
		} catch (error) {
			setMessage('Failed to register');
		}
	};

	const handleEmailVerification = async () => {
		try {
			const response = await api.post('/emailCheck', {
				email: formData.userEmail
			});
			if (response.status === 200) {
				setMessage('A verification code has been sent to your email.');
			}
		} catch (error: any ) {
			if (error.response) {
				if(error.response.status === 400) {
					setMessage(error.response.data || 'Email already in use')
				}
			} else {
				setMessage('Failed to send verification code. {}');
			}
		}
	};

	const verifyEmailToken = async (authCode: string) => {
		try {
			const response = await api.post('/verifyToken', {
				email: formData.userEmail,
				authCode: authCode
			});
			if (response.status === 200) {
				setEmailVerified(true);
				setMessage('Email verified successfully');
			} else {
				setMessage('Invalid or expired token');
			}
		} catch (error) {
			setMessage('Email verification failed');
		}
	};

	const checkNicknameAvailability = async () => {
			try {
				const response = await api.get(`/auth/isExist/${formData.nickName}`);

				if (response.data) {
					setNicknameAvailable(false);
					setMessage('Nickname is already taken. Please choose another one.');
				} else {
					setNicknameAvailable(true);
					setMessage('Nickname is available!');
				}
			} catch
				(error) {
				console.error('Error checking nickname availability: ', error);
				setMessage('Failed to check nickname availability.');
			}
		}
	;

	return {
		formData,
		handleChange,
		handleSubmit,
		handleEmailVerification,
		verifyEmailToken,
		checkNicknameAvailability,
		message,
		emailVerified,
		nicknameAvailable,
	};
};

export default useSignUp;