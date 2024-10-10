import React, {useState, ChangeEvent, FormEvent} from 'react';
import api from '../../api/api'
import {PATH} from '../../constants/paths'
import {useNavigate} from "react-router-dom";

interface FormData {
	userName: string;
	nickName: string;
	userEmail: string;
	password: string;
	confirmPassword: string;
	role: string;
	verificationCode: string;
}

function SignUpPage() {
	const [formData, setFormData] = useState<FormData>({
		userName: '',
		nickName: '',
		userEmail: '',
		password: '',
		confirmPassword: '',
		role: '',
		verificationCode: '',
	});

	const [emailVerified, setEmailVerified] = useState(false);  // 이메일 인증 상태
	const [nicknameAvailable, setNicknameAvailable] = useState(false);  // 닉네임 사용 가능 상태
	const [message, setMessage] = useState('');
	const navigate = useNavigate();

	const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
		const {name, value} = e.target;
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
			const response = await api.post('/users/register', formData);
			setMessage('Registration successful!');
			setTimeout(() => {
				navigate(PATH.LOGIN);
			},1000);
		} catch (error) {
			setMessage('Failed to register');
		}
	};

	const handleEmailVerification = async () => {
		try {
			const response = await api.post('/emailCheck', {
				email: formData.userEmail
			});
			setMessage('A verification code has been sent to your email.');
		} catch (error) {
			setMessage('Failed to send verification code.');
		}
	};

	const verifyEmailToken = async (token: string) => {
		try {
			const response = await api.post('/verifyToken', {
				email: formData.userEmail,
				token: token
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
				const response = await api.get(`/users/isExist/${formData.nickName}`);

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

	return (
		<div className="container mx-auto px-4">
			<form onSubmit={handleSubmit} className="mt-8 max-w-md mx-auto">
				{message && <div className="mb-4 text-center font-medium text-red-600">{message}</div>}
				<div className="mb-6">
					<label htmlFor="userName" className="block mb-2 text-sm font-medium text-gray-900">User
						Name</label>
					<input type="text" id="userName" name="userName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="User Name" value={formData.userName} onChange={handleChange}/>
				</div>
				<div className="mb-6">
					<label htmlFor="nickName" className="block mb-2 text-sm font-medium text-gray-900">Nick
						Name</label>
					<input type="text" id="nickName" name="nickName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Nick Name" value={formData.nickName} onChange={handleChange}/>
					<button type="button" onClick={checkNicknameAvailability}
					        className="text-white bg-blue-500 hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2.5 mr-2">
						Check Nickname
					</button>
				</div>
				<div className="mb-6">
					<label htmlFor="userEmail"
					       className="block mb-2 text-sm font-medium text-gray-900">Email</label>
					<input type="email" id="userEmail" name="userEmail" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Email" value={formData.userEmail} onChange={handleChange}/>
					<button type="button" onClick={handleEmailVerification}
					        className="text-white bg-green-500 hover:bg-green-600 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2.5">
						Verify Email
					</button>
				</div>
				<div className="mb-6">
					<label htmlFor="verificationCode" className="block mb-2 text-sm font-medium text-gray-900">Verification
						Code</label>
					<input type="text" id="verificationCode" name="verificationCode"
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Enter your verification code" value={formData.verificationCode}
					       onChange={handleChange}/>
					<button type="button" onClick={() => verifyEmailToken(formData.verificationCode)}
					        className="text-white bg-green-500 hover:bg-green-600 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2.5">
						Verify Code
					</button>
				</div>


				<div className="mb-6">
					<label htmlFor="password"
					       className="block mb-2 text-sm font-medium text-gray-900">Password</label>
					<input type="password" id="password" name="password" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Password" value={formData.password} onChange={handleChange}/>
				</div>
				<div className="mb-6">
					<label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium text-gray-900">Confirm
						Password</label>
					<input type="password" id="confirmPassword" name="confirmPassword" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Confirm Password" value={formData.confirmPassword} onChange={handleChange}/>
				</div>
				<button type="submit"
				        className="text-white bg-blue-500 hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
					Sign up
				</button>
			</form>
		</div>
	);
}

export default SignUpPage;
