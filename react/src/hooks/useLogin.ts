import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import {toast} from 'react-toastify';
import api from '../api/api';
import { login } from '../api/auth';
import useUserStore from '../store/useUserStore';
import {JSEncrypt} from 'jsencrypt';
import {PATH} from '../constants/paths';
import {LoginFormData} from "../@types/formTypes";

const useLogin = () => {
	const [formData, setFormData] = useState<LoginFormData>({email: '', password:''});
	const [error, setError] = useState('');
	const [publicKey, setPublicKey] = useState('');
	const navigate = useNavigate();
	const {setUser} = useUserStore();

	useEffect(() => {
		fetchPublicKey();
	}, []);

	// publicKey 생성
	const fetchPublicKey = async () => {
		try {
			const response = await api.get<string>('auth/public-key');
			setPublicKey(response.data);
			console.log("public Key: ", response.data)
		} catch (error) {
			console.error("Failed to fetch public key: ", error);
			setError("Failed to fetch public key, Please try again");
		}
	}
	// 입력한 password 암호화
	const encryptPassword = (password: string):string | null => {
		try {
			if (!publicKey) {
				throw new Error("Public key is not available or invalid.");
			}
			const encrypt = new JSEncrypt();
			encrypt.setPublicKey(publicKey);
			const encrypted = encrypt.encrypt(password);

			if (!encrypted) {
				throw new Error("Encryption failed");
			}
			console.log("Encrypted password:", encrypted);
			return encrypted;
		} catch (error) {
			console.error("Encryption error:", error);
			throw error;
		}
	};

	const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		try {
			const encryptedPassword = encryptPassword(formData.password);
			if (!encryptedPassword) {
				throw new Error("Failed to encrypt password");
			}
			const response = await login(formData.email, encryptedPassword);
			setUser(response);
			toast.success('Login Successful');
			setTimeout(() => {
				navigate(PATH.HOME);
			}, 2000);
		} catch (err: unknown) {
			console.error("Login error:", err);
			if (err instanceof Error) {
				setError(`Login failed: ${err.message}`);
			} else {
				setError("Login failed: An unexpected error occurred");
			}
		}
	};

	return {
		formData,
		setFormData,
		error,
		handleLogin
	};
};

export default useLogin;